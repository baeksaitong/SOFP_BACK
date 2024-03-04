package baeksaitong.sofp.domain.member.service;

import baeksaitong.sofp.domain.member.repository.MemberRepository;
import baeksaitong.sofp.global.common.entity.Member;
import baeksaitong.sofp.global.s3.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final AwsS3Service awsS3Service;
    private final MemberRepository memberRepository;

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
}
