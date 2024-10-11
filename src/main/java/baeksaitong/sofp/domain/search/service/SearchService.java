package baeksaitong.sofp.domain.search.service;

import baeksaitong.sofp.domain.favorite.repository.FavoriteRepository;
import baeksaitong.sofp.domain.health.entity.DiseaseAllergy;
import baeksaitong.sofp.domain.health.repository.ProfileDiseaseAllergyRepository;
import baeksaitong.sofp.domain.history.service.HistoryService;
import baeksaitong.sofp.domain.pill.repository.PillRepository;
import baeksaitong.sofp.domain.profile.service.ProfileService;
import baeksaitong.sofp.domain.search.dto.ai.AIAnalyzeDto;
import baeksaitong.sofp.domain.search.dto.pillInfo.Doc;
import baeksaitong.sofp.domain.search.dto.request.ImageReq;
import baeksaitong.sofp.domain.search.dto.KeywordSearchDto;
import baeksaitong.sofp.domain.search.dto.response.*;
import baeksaitong.sofp.domain.search.dto.pillInfo.PillInfoDto;
import baeksaitong.sofp.domain.search.error.SearchErrorCode;
import baeksaitong.sofp.domain.search.feign.AIFeignClient;
import baeksaitong.sofp.domain.search.feign.PillFeignClient;
import baeksaitong.sofp.domain.favorite.entity.Favorite;
import baeksaitong.sofp.domain.pill.entity.Pill;
import baeksaitong.sofp.domain.profile.entity.Profile;
import baeksaitong.sofp.global.error.exception.BusinessException;
import baeksaitong.sofp.global.util.EncryptionUtil;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final PillRepository pillRepository;
    private final PillFeignClient pillFeignClient;
    private final AIFeignClient aiFeignClient;
    private final FavoriteRepository favoriteRepository;
    private final HistoryService historyService;
    private final ProfileService profileService;
    private final ProfileDiseaseAllergyRepository profileDiseaseAllergyRepository;

    @Value("${api.public-data.url.pill-info}")
    private String pillInfoUrl;

    @Value("${api.public-data.serviceKey}")
    private String serviceKey;

    public KeywordRes findByKeyword(String encryptedProfileId, int limit, String encryptedLastId, String keyword, String shape, String sign, String color, String formulation, String line) {
        Long profileId = EncryptionUtil.decrypt(encryptedProfileId);
        Long lastId = EncryptionUtil.decrypt(encryptedLastId);

        Profile profile = profileService.getProfile(profileId);

        KeywordSearchDto req = KeywordSearchDto.builder()
                .keyword(keyword)
                .line(line)
                .shape(shape)
                .sign(sign)
                .color(color)
                .formulation(formulation)
                .limit(limit)
                .lastId(lastId)
                .build();

        List<Pill> result = pillRepository.findByKeyword(req);

        List<KeywordDto> results = getKeywordDto(result, profile);

        return new KeywordRes(results);
    }

    public List<KeywordDto> getKeywordDto(List<Pill> pillList, Profile profile) {
        Set<String> allergyAndDiseaseList = getAllergyAndDiseaseSet(profile);

        List<Favorite> favoriteList = favoriteRepository.findAllByProfileAndPillIn(profile, pillList);
        Map<Long, Favorite> favoriteMap = favoriteList.stream()
                .collect(Collectors.toMap(favorite -> favorite.getPill().getId(), fav -> fav));

        return pillList.stream()
                .map(pill -> {
                    Favorite favorite = favoriteMap.get(pill.getId());
                    return new KeywordDto(
                            pill,
                            checkIsWaring(pill.getSerialNumber().toString(), allergyAndDiseaseList),
                            (favorite != null) ? favorite.getId() : null
                    );
                }).collect(Collectors.toList());
    }

    public PillInfoRes getPillInfo(Long serialNumber, String encryptedProfileId) {
        Long profileId = EncryptionUtil.decrypt(encryptedProfileId);

        Profile profile = profileService.getProfile(profileId);

        PillInfoDto result = callPillInfoAPI(String.valueOf(serialNumber));

        if(result.getName() == null){
            throw new BusinessException(SearchErrorCode.PILL_INFO_ERROR);
        }

        List<String> allWaring = new ArrayList<>();

        if(result.getCautionGeneral() != null){
            Set<String> diseaseAllergy = getAllergyAndDiseaseSet(profile);
             allWaring = findAllWaringInfo(result.getCautionGeneral(), diseaseAllergy);
        }

        Doc efficacyEffect = parseXML(result.getEfficacyEffect());
        Doc dosageUsage = parseXML(result.getDosageUsage());
        Doc cautionGeneral = parseXML(result.getCautionGeneral());
        Doc cautionProfessional = parseXML(result.getCautionProfessional());

        PillInfoRes res = new PillInfoRes(result, efficacyEffect, dosageUsage, cautionGeneral, cautionProfessional, allWaring);

        if(profile != null) {
            historyService.addRecentView(profile.getId(), serialNumber);
        }

        return res;

    }

    private PillInfoDto callPillInfoAPI(String serialNumber) {
        try {
            return pillFeignClient.getPillInfo(new URI(pillInfoUrl), serviceKey, "json", serialNumber);

        } catch (URISyntaxException e) {
            throw new BusinessException(SearchErrorCode.PILL_INFO_ERROR);
        }
    }

    public ImageRes findByImage(ImageReq req) {

        AIAnalyzeDto aiAnalyzeReq = aiFeignClient.getAIAnalyze(req.getImages().get(0));

        List<KeywordDto> result = findByKeyword(req.getProfileId(), req.getLimit(), req.getLastId(), null, aiAnalyzeReq.getShape(), null, aiAnalyzeReq.getColor().get(0), null, null).result();
        return new ImageRes(
                new FilterDto(aiAnalyzeReq.getShape(), null, null, null, null, aiAnalyzeReq.getColor().get(0), null),
                result
        );

    }

    private Set<String> getAllergyAndDiseaseSet(Profile profile) {
        return profileDiseaseAllergyRepository.findAllDiseaseAllergyByProfile(profile)
                .stream().map(DiseaseAllergy::getName).collect(Collectors.toSet());
    }

    private Boolean checkIsWaring(String serialNumber, Set<String> allergyAndDiseaseSet){
        String cautionGeneral = callPillInfoAPI(serialNumber).getCautionGeneral();
        return allergyAndDiseaseSet.stream().anyMatch(cautionGeneral::contains);
    }

    private List<String> findAllWaringInfo(String cautionGeneral, Set<String> allergyAndDiseaseSet) {
        return allergyAndDiseaseSet.stream()
                .filter(cautionGeneral::contains)
                .collect(Collectors.toList());
    }

    private Doc parseXML(String input) {
        if (input == null) {
            return new Doc();
        }

       try {
           JAXBContext jaxbContext = JAXBContext.newInstance(Doc.class);
           Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
           StringReader reader = new StringReader(input);
           return (Doc) unmarshaller.unmarshal(reader);

       } catch (JAXBException e){
           throw new BusinessException(SearchErrorCode.FAILURE_PARSE_XML);
       }
    }
}
