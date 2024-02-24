package baeksaitong.sofp.domain.verification.service;

import baeksaitong.sofp.global.common.service.RedisService;
import baeksaitong.sofp.domain.verification.error.MailErrorCode;
import baeksaitong.sofp.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

import static baeksaitong.sofp.global.common.Constants.SING_UP_FLAG;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final MailService mailService;
    private final RedisService redisService;

    private final int CODE_LENGTH = 6;

    @Value("${spring.mail.auth-code-expiration-minutes}")
    private int CODE_MINUTE;

    public void sendEmailCode(String email) {
        String code = makeRandomNumber();
        String title = "회원가입 인증 이메일 입니다.";
        String content =
                "인증번호 : " +  code +
                        "<br> 유효시간은 " + CODE_MINUTE + "분 입니다." +
                        "<br> 인증번호를 제대로 입력해주세요";
        String key = SING_UP_FLAG + code;

        mailService.mailSend(email,title,content);
        redisService.setValues(email, key, Duration.ofMinutes(CODE_MINUTE));
    }

    private String makeRandomNumber() {
        Random r = new Random();
        StringBuilder randomNumber = new StringBuilder();
        for(int i = 0; i < CODE_LENGTH; i++) {
            randomNumber.append(r.nextInt(10));
        }

        return randomNumber.toString();
    }

    public boolean checkEmailCode(String email, String code) {
        String key = SING_UP_FLAG + email;
        
        if(!redisService.hasKey(key)){
            throw new BusinessException(MailErrorCode.EXPIRED_VERIFICATION_CODE);
        }

        if(!code.equals(redisService.getValues(key))){
            throw new BusinessException(MailErrorCode.INCORRECT_VERIFICATION_CODE);
        }
        redisService.deleteValues(key);

        return true;
    }
}
