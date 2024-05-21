package baeksaitong.sofp.domain.health.service;

import baeksaitong.sofp.domain.health.dto.request.DiseaseAllergyEditReq;
import baeksaitong.sofp.domain.health.dto.response.DiseaseAllergyRes;
import baeksaitong.sofp.domain.health.repository.DiseaseAllergyRepository;
import baeksaitong.sofp.domain.health.repository.ProfileDiseaseAllergyRepository;
import baeksaitong.sofp.domain.profile.service.ProfileService;
import baeksaitong.sofp.global.common.entity.DiseaseAllergy;
import baeksaitong.sofp.global.common.entity.Profile;
import baeksaitong.sofp.global.common.entity.ProfileDiseaseAllergy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DiseaseAllergyService {
    private final DiseaseAllergyRepository diseaseAllergyRepository;
    private final ProfileService profileService;
    private final ProfileDiseaseAllergyRepository profileDiseaseAllergyRepository;

    public List<String> getAllDiseaseAllergyList() {
        return diseaseAllergyRepository.findAll().stream()
                .map(DiseaseAllergy::getName)
                .collect(Collectors.toList());
    }

    public List<String> searchDiseaseAllergyList(String keyword) {
        return diseaseAllergyRepository.findByKeyword(keyword);
    }

    public DiseaseAllergyRes getDiseaseAllergyList(Long profileId) {
        Profile profile = profileService.getProfile(profileId);

        return new DiseaseAllergyRes(
                profileDiseaseAllergyRepository.findAllByProfile(profile)
                        .stream()
                        .map(profileDiseaseAllergy -> profileDiseaseAllergy.getDiseaseAllergy().getName())
                        .collect(Collectors.toList())
        );
    }

    public DiseaseAllergyRes editDiseaseAllergy(DiseaseAllergyEditReq req, Long profileId) {
        Profile profile = profileService.getProfile(profileId);

        List<String> addListReq = req.getAddDiseaseAllergyList();
        List<String> delListReq = req.getRemoveDiseaseAllergyList();
        List<ProfileDiseaseAllergy> existList  = profileDiseaseAllergyRepository.findAllByProfile(profile);

        Set<String> existSet = existList.stream()
                .map(profileDiseaseAllergy -> profileDiseaseAllergy.getDiseaseAllergy().getName())
                .collect(Collectors.toSet());

        if(addListReq.isEmpty() && delListReq.isEmpty()){
            return new DiseaseAllergyRes(
                    existSet.stream().toList()
            );
        }

        Set<String> saveList = addDiseaseAllergy(profile, addListReq, existList)
                .stream()
                .map(profileDiseaseAllergy -> profileDiseaseAllergy.getDiseaseAllergy().getName())
                .collect(Collectors.toSet());
        Set<String> removeList = delDiseaseAllergy(delListReq, existList)
                .stream()
                .map(profileDiseaseAllergy -> profileDiseaseAllergy.getDiseaseAllergy().getName())
                .collect(Collectors.toSet());

        existSet.addAll(saveList);
        existSet.removeAll(removeList);


        return new DiseaseAllergyRes(
                existSet.stream().toList()
        );

    }


    private List<ProfileDiseaseAllergy> addDiseaseAllergy(Profile profile, List<String> addList, List<ProfileDiseaseAllergy> existProfileDiseaseAllergyList) {

        List<DiseaseAllergy> diseaseAllergyList = diseaseAllergyRepository.findByNameIn(addList);
        List<DiseaseAllergy> existList =  existProfileDiseaseAllergyList.stream()
                .map(ProfileDiseaseAllergy::getDiseaseAllergy)
                .toList();

        List<ProfileDiseaseAllergy> saveList = new ArrayList<>();

        for (DiseaseAllergy diseaseAllergy : diseaseAllergyList){
            if (existList.contains(diseaseAllergy)) {
                continue;
            }

            ProfileDiseaseAllergy profileDiseaseAllergy = ProfileDiseaseAllergy.builder()
                    .profile(profile)
                    .diseaseAllergy(diseaseAllergy)
                    .build();

            saveList.add(profileDiseaseAllergy);

        }

        if (!saveList.isEmpty()) {
            profileDiseaseAllergyRepository.saveAll(saveList);
        }

        return saveList;

    }

    private List<ProfileDiseaseAllergy> delDiseaseAllergy( List<String> delList, List<ProfileDiseaseAllergy> existProfileDiseaseAllergyList) {

        List<DiseaseAllergy> diseaseAllergyList = diseaseAllergyRepository.findByNameIn(delList);

        Map<DiseaseAllergy, ProfileDiseaseAllergy> existMap =  existProfileDiseaseAllergyList.stream()
                .collect(Collectors.toMap(ProfileDiseaseAllergy::getDiseaseAllergy, pda -> pda));

        List<ProfileDiseaseAllergy> removeList = diseaseAllergyList.stream()
                .map(existMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (!removeList.isEmpty()) {
            profileDiseaseAllergyRepository.deleteAll(removeList);
        }

        return removeList;

    }
}
