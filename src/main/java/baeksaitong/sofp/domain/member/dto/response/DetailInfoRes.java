package baeksaitong.sofp.domain.member.dto.response;

import baeksaitong.sofp.global.common.entity.enums.MemberGender;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record DetailInfoRes(
        @Schema(description = "이름")
        String name,
        @Schema(description = "생일")
        LocalDate birthday,
        @Schema(description = "이메일")
        String email,
        @Schema(description = "닉네임")
        String nickname,
        @Schema(description = "프로필 사진 주소")
        String imgUrl,
        @Schema(description = "성별")
        MemberGender gender,
        @Schema(description = "광고 동의 여부")
        Boolean advertisement

) {
}
