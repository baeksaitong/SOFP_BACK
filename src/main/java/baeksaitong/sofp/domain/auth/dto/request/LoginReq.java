package baeksaitong.sofp.domain.auth.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginReq {
    private String id;
    private String password;
}
