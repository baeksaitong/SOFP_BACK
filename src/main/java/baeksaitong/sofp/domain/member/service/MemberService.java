package baeksaitong.sofp.domain.member.service;

import baeksaitong.sofp.global.common.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MailService mailService;
    private final RedisService redisService;

    private final int CODE_LENGTH = 6;

    @Value("${auth-code-expiration-minutes}")
    private final long CODE_MINUTE;

    public void sendEmailCode(String email) {
        String code = makeRandomNumber();
        String title = "회원가입 인증 이메일 입니다.";
        String content =
                "인증번호 : " +  code +
                        "<br> 유효시간은 " + CODE_MINUTE + "분 입니다." +
                        "<br> 인증번호를 제대로 입력해주세요";

        mailService.mailSend(email,title,content);
        redisService.setValues(email, code, Duration.ofMinutes(CODE_MINUTE));
    }

    private String makeRandomNumber() {
        Random r = new Random();
        StringBuilder randomNumber = new StringBuilder();
        for(int i = 0; i < CODE_LENGTH; i++) {
            randomNumber.append(r.nextInt(10));
        }

        return randomNumber.toString();
    }
}
