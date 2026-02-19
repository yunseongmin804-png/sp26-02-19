package com.example.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

// 반드시 아래의 MimeMessage를 사용해야 합니다 (ws.mime 아님!)
import jakarta.mail.internet.MimeMessage; 

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender mailSender;

    // 1. 일반 텍스트 메일 전송
    public void sendTextEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    // 2. 웰컴 메일 전송 (HTML 형식)
    public void sendWelcomeEmail(String userEmail, String nickname) {
        String subject = nickname + "님, 환영합니다!";
        String htmlContent = """
            <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;">
                <h2 style="color: #4CAF50;">환영합니다!</h2>
                <p>안녕하세요, <strong>%s</strong>님!</p>
                <p>회원가입을 축하드립니다.</p>
            </div>
            """.formatted(nickname);
        
        sendHtmlEmail(userEmail, subject, htmlContent);
    }

    // 3. HTML 메일 실제 발송 로직
    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); 
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}