package baeksaitong.sofp.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record PillRes(
        @Schema(description = "사용자가 현제 복용중인 알약 정보 리스트")
        List<PillInfoRes> pillInfoList
) {

}
