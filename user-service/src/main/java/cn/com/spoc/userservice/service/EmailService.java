package cn.com.spoc.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final String messageSubject = "【SPOC】验证码";
    @Value("${spring.mail.username}")
    private String messageFrom;
    @Autowired
    private JavaMailSender mailSender;

    public void sendRegisterVerificationCode(String toEmail, String code) {
        var message = new SimpleMailMessage();
        message.setFrom(messageFrom);
        message.setTo(toEmail);
        message.setSubject(messageSubject);
        message.setText("【SPOC】验证码：" + code + "（5分钟内有效）。您正在注册SPOC平台账户，请勿将验证码告诉他人哦。");
        mailSender.send(message);
    }

    public void sendResetPasswordVerificationCode(String toEmail, String code) {
        var message = new SimpleMailMessage();
        message.setFrom(messageFrom);
        message.setTo(toEmail);
        message.setSubject(messageSubject);
        message.setText("【SPOC】验证码：" + code + "（5分钟内有效）。您正在重置账号密码，请勿将验证码告诉其他人哦。");
        mailSender.send(message);
    }
}
