package baeksaitong.sofp.domain.member.dto.response;

import baeksaitong.sofp.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;

public record MemberRes(
        @Schema(description = "이메일")
        String email,
        @Schema(description = "광고 동의 여부")
        Boolean advertisement

) {
        public MemberRes(Member member){
                this(member.getEmail(), member.getAdvertisement());
        }
}
