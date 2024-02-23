package baeksaitong.sofp.domain.member.service;

import baeksaitong.sofp.global.error.dto.BusinessErrorCode;
import baeksaitong.sofp.global.error.exception.BusinessException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    @Value("${spring.mail.username}")
    private String sender;
    private final JavaMailSender mailSender;

    public void mailSend(String toMail, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
            helper.setFrom(sender);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content,true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new BusinessException(BusinessErrorCode.FAILED_SEND_EMAIL);
        }


    }
}
