package cn.com.spoc.userservice.controller;

import cn.com.spoc.userservice.service.EmailService;
import cn.com.spoc.userservice.service.RedisService;
import cn.com.spoc.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/verification-code")
public class VerificationCodeController {
    private final Random random = new Random();
    @Autowired
    private RedisService redisService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;

    @PostMapping("/send-verification-code")
    public ResponseEntity<Map<String, String>> sendVerificationCode(@RequestBody Map<String, String> request) {
        var email = request.get("email");
        var code = generateVerificationCode();
        redisService.saveVerificationCode(email, code);
        emailService.sendRegisterVerificationCode(email, code);
        return ResponseEntity.ok(Map.of("message", "Verification Code Sent Successfully"));
    }

    @PostMapping("/send-reset-password-verification-code")
    public ResponseEntity<Map<String, String>> sendResetPasswordVerificationCode(@RequestBody Map<String, String> request) {
        var username = request.get("username");
        var email = userService.getEmailByUsername(username);
        if (email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "No Such Username"));
        }
        var code = generateVerificationCode();
        redisService.saveVerificationCode(email, code);
        emailService.sendResetPasswordVerificationCode(email, code);
        return ResponseEntity.ok(Map.of("message", "Verification Code Sent to " + email + " Successfully"));
    }

    private String generateVerificationCode() {
        return "%06d".formatted(random.nextInt(1000000));
    }
}
