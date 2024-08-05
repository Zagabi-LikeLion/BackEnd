package org.likelion.zagabi.Domain.Email.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.likelion.zagabi.Domain.Email.Dto.EmailVerifyDto;
import org.likelion.zagabi.Global.Util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;
    private final String ePw = createKey();
    private static final String AUTH_CODE_PREFIX = "AuthCode ";

    @Value("${spring.mail.username}")
    private String id;

    @Value("${spring.mail.smtp.timeout}")
    private long codeExpTime;

    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
        log.info("보내는 대상: " + to);
        log.info("인증 번호: " + ePw);
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to); // 보낼 대상
        message.setSubject("자가비 인증 코드"); // 메일 제목

        String msg = getString();

        message.setText(msg, "utf-8", "html"); //내용, charset타입, subtype
        message.setFrom(new InternetAddress(id,"Zagabi"));

        return message;
    }

    private String getString() {
        String msg="";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 화면에서 입력해주세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += ePw;
        msg += "</td></tr></tbody></table></div>";
        return msg;
    }

    public static String createKey() {
        StringBuilder key = new StringBuilder();
        Random rnd = new Random();

        // 인증코드 6자리
        for (int i = 0; i < 6; i++) {
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }

    @Transactional
    public String sendMessage(String to) throws Exception {
        MimeMessage message = createMessage(to);
        try {
            javaMailSender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException("이메일을 보낼 수 없습니다.");
        }
        redisUtil.save(AUTH_CODE_PREFIX + to, ePw, codeExpTime, TimeUnit.MILLISECONDS);
        return ePw;
    }

    @Transactional
    public boolean verifyCode(EmailVerifyDto requestDto) {
        if (!(redisUtil.hasKey(AUTH_CODE_PREFIX + requestDto.email()))) {
            throw new IllegalArgumentException("코드가 유효하지 않습니다.");
        }
        else if (redisUtil.get(AUTH_CODE_PREFIX + requestDto.email()).toString().equals(requestDto.code())) {
            return true;
        }
        else {
            throw new IllegalArgumentException("코드가 유효하지 않습니다.");
        }
    }
}
