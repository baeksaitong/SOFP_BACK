package baeksaitong.sofp.domain.auth.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LoginReq {
    private String id;
    private String password;
}
