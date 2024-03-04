package baeksaitong.sofp.domain.member.service;

import baeksaitong.sofp.domain.health.error.AllergyErrorCode;
import baeksaitong.sofp.domain.health.repository.AllergyRepository;
import baeksaitong.sofp.domain.member.repository.MemberAllergyRepository;
import baeksaitong.sofp.domain.member.repository.MemberRepository;
import baeksaitong.sofp.global.common.entity.Allergy;
import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.common.entity.MemberAllergy;
import baeksaitong.sofp.global.error.exception.BusinessException;
import baeksaitong.sofp.global.s3.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final AwsS3Service awsS3Service;
    private final MemberRepository memberRepository;
    private final MemberAllergyRepository memberAllergyRepository;
    private final AllergyRepository allergyRepository;

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

    public void setAllergy(List<String> allergyList, Member member) {
        if(allergyList.isEmpty()){
            return;
        }

        for (String name : allergyList) {
            Allergy allergy = allergyRepository.findByName(name).orElseThrow(
                    () -> new BusinessException(AllergyErrorCode.NO_SUCH_ALLERGY)
            );
            MemberAllergy memberAllergy = MemberAllergy.builder()
                    .member(member)
                    .allergy(allergy)
                    .build();

            memberAllergyRepository.save(memberAllergy);
        }
    }
}
