package baeksaitong.sofp.domain.verification.service;

import baeksaitong.sofp.global.redis.RedisService;
import baeksaitong.sofp.domain.verification.error.MailErrorCode;
import baeksaitong.sofp.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

import static baeksaitong.sofp.global.redis.RedisPrefix.*;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final MailService mailService;
    private final RedisService redisService;

    @Value("${spring.mail.auth-code-expiration-minutes}")
    public static int SIGN_CODE_MINUTE;

    public void sendEmailCode(String email) {
        String code = makeRandomNumber(6);
        String title = "회원가입 인증 이메일 입니다.";
        String content =
                "인증번호 : " +  code +
                        "<br> 유효시간은 " + SIGN_CODE_MINUTE + "분 입니다." +
                        "<br> 인증번호를 제대로 입력해주세요";

        mailService.mailSend(email,title,content);
        redisService.save(SING_UP, email, code, SING_UP.getDuration());
    }

    private String makeRandomNumber(int codeLength) {
        Random r = new Random();
        StringBuilder randomNumber = new StringBuilder();
        for(int i = 0; i < codeLength; i++) {
            randomNumber.append(r.nextInt(10));
        }

        return randomNumber.toString();
    }

    public boolean checkEmailCode(String email, String code) {

        if(redisService.hasNoKey(SING_UP, email)){
            throw new BusinessException(MailErrorCode.EXPIRED_VERIFICATION_CODE);
        }

        if(!code.equals(redisService.get(SING_UP, email))){
            throw new BusinessException(MailErrorCode.INCORRECT_VERIFICATION_CODE);
        }

        redisService.delete(SING_UP, email);

        return true;
    }
}
