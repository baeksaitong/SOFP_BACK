package baeksaitong.sofp.domain.member.service;

import baeksaitong.sofp.domain.member.dto.response.BasicInfoRes;
import baeksaitong.sofp.domain.auth.error.AuthErrorCode;
import baeksaitong.sofp.domain.health.error.PillErrorCode;
import baeksaitong.sofp.domain.health.repository.DiseaseAllergyRepository;
import baeksaitong.sofp.domain.health.repository.PillRepository;
import baeksaitong.sofp.domain.history.service.HistoryService;
import baeksaitong.sofp.domain.member.dto.request.MemberEditReq;
import baeksaitong.sofp.domain.member.dto.response.DetailInfoRes;
import baeksaitong.sofp.domain.member.dto.response.PillInfoRes;
import baeksaitong.sofp.domain.member.dto.response.PillRes;
import baeksaitong.sofp.domain.member.repository.MemberDiseaseAllergyRepository;
import baeksaitong.sofp.domain.member.repository.MemberPillRepository;
import baeksaitong.sofp.domain.member.repository.MemberRepository;
import baeksaitong.sofp.domain.search.dto.response.KeywordRes;
import baeksaitong.sofp.domain.search.service.SearchService;
import baeksaitong.sofp.global.common.entity.*;
import baeksaitong.sofp.global.common.entity.enums.MemberGender;
import baeksaitong.sofp.global.error.exception.BusinessException;
import baeksaitong.sofp.global.s3.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final AwsS3Service awsS3Service;
    private final MemberRepository memberRepository;
    private final MemberDiseaseAllergyRepository memberDiseaseAllergyRepository;
    private final MemberPillRepository memberPillRepository;
    private final DiseaseAllergyRepository diseaseAllergyRepository;
    private final PillRepository pillRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HistoryService historyService;
    private final SearchService searchService;


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

    public void editMember(MemberEditReq req, Member member) {
        member.setNickname(req.getNickname());
        member.setGender(MemberGender.from(req.getGender()));
        if(req.getPassword() != null) member.setPwd(passwordEncoder.encode(req.getPassword()));
        member.setBirthday(req.getBirthday());
        member.setAdvertisement(req.getAdvertisement());

        memberRepository.save(member);
    }

    public List<String> getgetDiseaseAllergyList(Member member) {
        return memberDiseaseAllergyRepository.findAllByMember(member).stream()
                .map(memberDiseaseAllergy -> memberDiseaseAllergy.getDiseaseAllergy().getName())
                .collect(Collectors.toList());
    }

    public void setDiseaseAllergy(List<String> diseaseAndAllergyList, Member member) {
        if(diseaseAndAllergyList.isEmpty()){
            return;
        }

        for (String name : diseaseAndAllergyList) {
            DiseaseAllergy diseaseAllergy = diseaseAllergyRepository.findByName(name).orElse(null);

            if(diseaseAllergy == null){
                continue;
            }

            if (memberDiseaseAllergyRepository.existsByMemberAndDiseaseAllergy(member, diseaseAllergy)) {
                continue;
            }

            MemberDiseaseAllergy memberDiseaseAllergy = MemberDiseaseAllergy.builder()
                    .member(member)
                    .diseaseAllergy(diseaseAllergy)
                    .build();

            memberDiseaseAllergyRepository.save(memberDiseaseAllergy);
        }
    }

    public void removeDiseaseAllergy(List<String> diseaseList, Member member) {
        if(diseaseList.isEmpty()){
            return;
        }

        for (String name : diseaseList) {
            DiseaseAllergy diseaseAllergy = diseaseAllergyRepository.findByName(name).orElse(null);

            if(diseaseAllergy == null){
                continue;
            }

            memberDiseaseAllergyRepository.deleteByMemberAndDiseaseAllergy(member, diseaseAllergy);
        }
    }

    public PillRes getPillList(Member member) {
        return new PillRes(
                memberPillRepository.findAllByMember(member).stream()
                        .map(
                                memberPill -> new PillInfoRes(memberPill.getPill().getId(), memberPill.getPill().getName())
                        ).collect(Collectors.toList()));
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



    public void removePill(List<Long> removePillIdList, Member member) {
        if(removePillIdList.isEmpty()){
            return;
        }

        for (Long PillId : removePillIdList) {
            Pill pill = pillRepository.findById(PillId).orElseThrow(() -> new BusinessException(PillErrorCode.NO_SUCH_PILL));
            memberPillRepository.deleteByMemberAndPill(member, pill);
        }
    }


    public KeywordRes getRecentViewPill(int count, Member member) {
        List<Long> recentViewPill = historyService.getRecentViewPill(member);

        if(recentViewPill == null){
            return new KeywordRes(0,new ArrayList<>());
        }


        List<Pill> pillList = new ArrayList<>();
        List<Long> errorPillList = new ArrayList<>();

        for (Long serialNumber : recentViewPill) {
            Pill pill = pillRepository.findBySerialNumber(serialNumber).orElse(null);
            if(pill != null){
                pillList.add(pill);
                if(pillList.size() > count){
                    break;
                }
            }else{
                errorPillList.add(serialNumber);
            }
        }

        if(!errorPillList.isEmpty()) {
            historyService.deleteRecentViewPill(member.getId(), errorPillList);
        }

        return new KeywordRes(count/5 + 1, searchService.getKeywordDto(pillList, member));
    }

    public KeywordRes deleteRecentViewPill(Long pillId, int count, Member member) {
        historyService.deleteRecentViewPill(member.getId(), List.of(new Long[]{pillId}));

        return getRecentViewPill(count, member);
    }

    public void verification(String password, Member member) {
        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new BusinessException(AuthErrorCode.WRONG_PASSWORD);
        }
    }

    public BasicInfoRes getBasicInfo(Member member) {
        return new BasicInfoRes(member.getNickname(), member.getImgUrl());
    }

    public DetailInfoRes getDetailInfo(Member member) {
        return new DetailInfoRes(
                member.getName(),
                member.getBirthday(),
                member.getEmail(),
                member.getNickname(),
                member.getImgUrl(),
                member.getGender(),
                member.getAdvertisement()
        );
    }
}
