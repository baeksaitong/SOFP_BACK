package baeksaitong.sofp.domain.member.service;

import baeksaitong.sofp.domain.health.error.AllergyErrorCode;
import baeksaitong.sofp.domain.health.error.DiseaseErrorCode;
import baeksaitong.sofp.domain.health.error.PillErrorCode;
import baeksaitong.sofp.domain.health.repository.AllergyRepository;
import baeksaitong.sofp.domain.health.repository.DiseaseRepository;
import baeksaitong.sofp.domain.health.repository.PillRepository;
import baeksaitong.sofp.domain.member.dto.request.MemberEditReq;
import baeksaitong.sofp.domain.member.repository.MemberAllergyRepository;
import baeksaitong.sofp.domain.member.repository.MemberDiseaseRepository;
import baeksaitong.sofp.domain.member.repository.MemberPillRepository;
import baeksaitong.sofp.domain.member.repository.MemberRepository;
import baeksaitong.sofp.global.common.entity.*;
import baeksaitong.sofp.global.error.exception.BusinessException;
import baeksaitong.sofp.global.s3.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final AwsS3Service awsS3Service;
    private final MemberRepository memberRepository;
    private final MemberAllergyRepository memberAllergyRepository;
    private final MemberDiseaseRepository memberDiseaseRepository;
    private final MemberPillRepository memberPillRepository;
    private final AllergyRepository allergyRepository;
    private final DiseaseRepository diseaseRepository;
    private final PillRepository pillRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public void setProfileImg(MultipartFile img, Member member) {
        if(member.getImgUrl() != null) {
            awsS3Service.deleteImage(member.getImgUrl());
        }

        String imgUrl = awsS3Service.upload(img, member.getId());
        member.setImgUrl(imgUrl);
        memberRepository.save(member);
    }

    public void setNickName(String nickname, Member member) {
        member.setNickname(nickname);
        memberRepository.save(member);
    }

    public List<String> getAllergy(Member member) {
        return memberAllergyRepository.findAllByMember(member).stream()
                .map(memberAllergy -> memberAllergy.getAllergy().getName())
                .collect(Collectors.toList());
    }

    public void setAllergy(List<String> allergyList, Member member) {
        if(allergyList.isEmpty()){
            return;
        }

        for (String name : allergyList) {
            Allergy allergy = allergyRepository.findByName(name).orElseThrow(
                    () -> new BusinessException(AllergyErrorCode.NO_SUCH_ALLERGY)
            );

            if (memberAllergyRepository.existsByMemberAndAllergy(member, allergy)) {
                continue;
            }

            MemberAllergy memberAllergy = MemberAllergy.builder()
                    .member(member)
                    .allergy(allergy)
                    .build();


            memberAllergyRepository.save(memberAllergy);

        }
    }

    public void removeAllergy(List<String> allergyList, Member member) {
        if(allergyList.isEmpty()){
            return;
        }

        for (String allergyName : allergyList) {
            Allergy allergy = allergyRepository.findByName(allergyName).orElseThrow(() -> new BusinessException(AllergyErrorCode.NO_SUCH_ALLERGY));
            memberAllergyRepository.deleteByMemberAndAllergy(member,allergy);
        }
    }

    public List<String> getDisease(Member member) {
        return memberDiseaseRepository.findAllByMember(member).stream()
                .map(memberDisease -> memberDisease.getDisease().getName())
                .collect(Collectors.toList());
    }

    public void setDisease(List<String> diseaseList, Member member) {
        if(diseaseList.isEmpty()){
            return;
        }



        for (String name : diseaseList) {
            Disease disease = diseaseRepository.findByName(name).orElseThrow(
                    () -> new BusinessException(DiseaseErrorCode.NO_SUCH_DISEASE)
            );

            if (memberDiseaseRepository.existsByMemberAndDisease(member, disease)) {
                continue;
            }

            MemberDisease memberDisease = MemberDisease.builder()
                    .member(member)
                    .disease(disease)
                    .build();

            memberDiseaseRepository.save(memberDisease);
        }
    }

    public void removeDisease(List<String> diseaseList, Member member) {
        if(diseaseList.isEmpty()){
            return;
        }

        for (String diseaseName : diseaseList) {
            Disease disease = diseaseRepository.findByName(diseaseName).orElseThrow(() -> new BusinessException(DiseaseErrorCode.NO_SUCH_DISEASE));
            memberDiseaseRepository.deleteByMemberAndDisease(member,disease);
        }
    }

    public void editMember(MemberEditReq req, Member member) {
        member.setNickname(req.getNickname());
        member.setGender(req.getGender());
        member.setPwd(passwordEncoder.encode(req.getPassword()));
        member.setBirthday(req.getBirthday());
        member.setAdvertisement(req.getAdvertisement());

        memberRepository.save(member);
    }


    public void setPill(List<Long> pillIdList, Member member) {
        if(pillIdList.isEmpty()){
            return;
        }

        for (Long pillId : pillIdList) {
            Pill pill = pillRepository.findById(pillId).orElseThrow(() -> new BusinessException(PillErrorCode.NO_SUCH_PILL));

            if(memberPillRepository.existsByMemberAndPill(member,pill)){
                continue;
            }

            MemberPill memberPill = MemberPill.builder()
                    .pill(pill)
                    .member(member)
                    .build();
            memberPillRepository.save(memberPill);
        }
    }
}
