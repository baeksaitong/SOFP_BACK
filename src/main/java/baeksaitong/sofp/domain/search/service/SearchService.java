package baeksaitong.sofp.domain.search.service;

import baeksaitong.sofp.domain.favorite.repository.FavoriteRepository;
import baeksaitong.sofp.domain.health.repository.ProfileDiseaseAllergyRepository;
import baeksaitong.sofp.domain.history.service.HistoryService;
import baeksaitong.sofp.domain.pill.repository.PillRepository;
import baeksaitong.sofp.domain.profile.service.ProfileService;
import baeksaitong.sofp.domain.search.dto.request.ImageReq;
import baeksaitong.sofp.domain.search.dto.request.KeywordReq;
import baeksaitong.sofp.domain.search.dto.response.KeywordDto;
import baeksaitong.sofp.domain.search.dto.response.KeywordRes;
import baeksaitong.sofp.domain.search.dto.response.PillInfoRes;
import baeksaitong.sofp.domain.search.error.SearchErrorCode;
import baeksaitong.sofp.domain.search.feign.PillFeignClient;
import baeksaitong.sofp.domain.favorite.entity.Favorite;
import baeksaitong.sofp.domain.pill.entity.Pill;
import baeksaitong.sofp.domain.profile.entity.Profile;
import baeksaitong.sofp.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final PillRepository pillRepository;
    private final PillFeignClient pillFeignClient;
    private final FavoriteRepository favoriteRepository;
    private final HistoryService historyService;
    private final ProfileService profileService;
    private final ProfileDiseaseAllergyRepository profileDiseaseAllergyRepository;

    @Value("${api.public-data.url.pill-info}")
    private String pillInfoUrl;

    @Value("${api.public-data.serviceKey}")
    private String serviceKey;

    public KeywordRes findByKeyword(Long profileId, String keyword, String shape, String sign, String color, String formulation, String line) {
        Profile profile = profileService.getProfile(profileId);

        KeywordReq req = KeywordReq.builder()
                .keyword(keyword)
                .line(line)
                .shape(shape)
                .sign(sign)
                .color(color)
                .formulation(formulation)
                .page(0)
                .limit(10)
                .build();

        Page<Pill> result = pillRepository.findByKeyword(req);

        List<KeywordDto> results = getKeywordDto(result.getContent(), profile);

        return new KeywordRes(result.getTotalPages(), results);
    }

    public List<KeywordDto> getKeywordDto(List<Pill> pillList, Profile profile) {
        Set<String> allergyAndDiseaseList = getAllergyAndDiseaseSet(profile);

        return pillList.stream()
                .map(pill -> {
                    Favorite favorite = favoriteRepository.findByPillAndProfile(pill, profile).orElse(null);
                    return new KeywordDto(
                            pill.getSerialNumber(),
                                pill.getName(),
                                pill.getClassification(),
                                pill.getProOrGen(),
                                pill.getImgUrl(),
                                pill.getChart(),
                                pill.getEnterprise(),
                                checkIsWaring(pill.getSerialNumber().toString(), allergyAndDiseaseList),
                                (favorite != null) ? favorite.getId() : null
                    );
                }).collect(Collectors.toList());
    }

    public PillInfoRes getPillInfo(String serialNumber, Long profileId) {

        Profile profile = null;

        if(profileId != null) {
            profile = profileService.getProfile(profileId);
        }

        PillInfoRes result;
        try {
             result = pillFeignClient.getPillInfo(new URI(pillInfoUrl), serviceKey, "json", serialNumber);
             if(result.getCautionGeneral() != null){
                 result.setWaringInfo(
                         findAllWaringInfo(result.getCautionGeneral(), getAllergyAndDiseaseSet(profile))
                 );
             }
        } catch (URISyntaxException e) {
            throw new BusinessException(SearchErrorCode.PILL_INFO_ERROR);
        }

        if(result.getName() == null){
            throw new BusinessException(SearchErrorCode.PILL_INFO_ERROR);
        }

        if(profile != null) {
            historyService.addRecentView(profile.getId(), Long.parseLong(serialNumber));
        }

        return result;

    }

    public void findByImage(ImageReq req) {

    }

    private Set<String> getAllergyAndDiseaseSet(Profile profile) {
        return profileDiseaseAllergyRepository.findAllByProfile(profile).stream()
                .map(memberDisease -> memberDisease.getDiseaseAllergy().getName()).collect(Collectors.toSet());
    }

    private Boolean checkIsWaring(String serialNumber, Set<String> allergyAndDiseaseSet){

        String cautionGeneral = getPillInfo(serialNumber, null).getCautionGeneral();
        return allergyAndDiseaseSet.stream().anyMatch(cautionGeneral::contains);
    }

    private List<String> findAllWaringInfo(String cautionGeneral, Set<String> allergyAndDiseaseSet) {
        return allergyAndDiseaseSet.stream()
                .filter(cautionGeneral::contains)
                .collect(Collectors.toList());
    }
}
