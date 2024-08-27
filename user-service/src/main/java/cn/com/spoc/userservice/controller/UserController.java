package cn.com.spoc.userservice.controller;

import cn.com.spoc.userservice.service.UserService;
import cn.com.spoc.userservice.service.TokenService;
import cn.com.spoc.userservice.service.RedisService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Value("${application.avatar-base-url}")
    private String avatarBaseUrl;
    @Value("${application.default-avatar-url}")
    private String defaultAvatarUrl;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, String> request) {
        var username = request.get("username");
        var password = request.get("password");
        var email = request.get("email");
        var code = request.get("code");
        var identity = request.get("identity");
        if (!redisService.getVerificationCode(email).equals(code)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Invalid Verification Code"));
        } else if (userService.existsUsername(username)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Username Exists"));
        }
        redisService.deleteVerificationCode(email);
        userService.registerUser(username, password, email, identity);
        return ResponseEntity.ok(Map.of("message", "Register Successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, String> request) {
        var username = request.get("username");
        var password = request.get("password");
        if (userService.isPasswordMatch(username, password)) {
            var token = tokenService.generateToken(username);
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Username Or Password"));
    }

    @PostMapping("/get-avatar-url")
    public ResponseEntity<Map<String, String>> getAvatarUrl(@RequestBody Map<String, String> request) {
        var token = request.get("token");
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token"));
        }
        var username = tokenService.getUsernameFromToken(token);
        if (userService.existsAvatar(username)) {
            return ResponseEntity.ok(Map.of("url", avatarBaseUrl + username));
        }
        return ResponseEntity.ok(Map.of("url", defaultAvatarUrl));
    }

    @PostMapping("/get-avatar-url-by-username")
    public ResponseEntity<Map<String, String>> getAvatarUrlByUsername(@RequestBody Map<String, String> request) {
        var username = request.get("username");
        if (userService.existsAvatar(username)) {
            return ResponseEntity.ok(Map.of("url", avatarBaseUrl + username));
        }
        return ResponseEntity.ok(Map.of("url", defaultAvatarUrl));
    }

    @GetMapping("/avatar/{username}")
    public void getAvatar(@PathVariable String username, HttpServletResponse response) {
        var avatar = userService.getAvatar(username);
        try {
            if (avatar == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                response.setContentType("image/jpeg");
                response.setContentLength(avatar.length);
                response.getOutputStream().write(avatar);
                response.getOutputStream().close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @PostMapping("/check-token")
    public ResponseEntity<Map<String, String>> checkToken(@RequestBody Map<String, String> request) {
        var token = request.get("token");
        return ResponseEntity.ok(Map.of("result", String.valueOf(tokenService.isTokenValid(token))));
    }


    @PostMapping("/get-avatar-url-user")
    public ResponseEntity<Map<String, String>> getAvatarUrlUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        if (userService.existsAvatar(username)) {
            return ResponseEntity.ok(Map.of("url", avatarBaseUrl + username));
        }
        return ResponseEntity.ok(Map.of("url", defaultAvatarUrl));
    }

    @PostMapping("/get-user-info")
    public ResponseEntity<Map<String, String>> getUserInfo(@RequestBody Map<String, String> request) {
        var username = request.get("username");
        var userInfo = userService.getUserInfo(username);
        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "No This User"));
        }
        return ResponseEntity.ok(Map.of(
                "username", userInfo.getUsername(),
                "identity", userInfo.getIdentity(),
                "email", userInfo.getEmail(),
                "signature", userInfo.getSignature()
        ));
    }

    @PostMapping("/edit-user-info")
    public ResponseEntity<Map<String, String>> editUserInfo(@RequestBody Map<String, String> request) {
        var token = request.get("token");
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token"));
        }
        var username = tokenService.getUsernameFromToken(token);
        var email = request.get("email");
        var signature = request.get("signature");
        userService.editUserInfo(username, email, signature);
        return ResponseEntity.ok(Map.of("message", "Edit User Info Successfully"));
    }

    @PostMapping("/update-password")
    public ResponseEntity<Map<String, String>> updatePassword(@RequestBody Map<String, String> request) {
        var token = request.get("token");
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token"));
        }
        var username = tokenService.getUsernameFromToken(token);
        var oldPassword = request.get("oldPassword");
        if (!userService.isPasswordMatch(username, oldPassword)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Incorrect Password"));
        }
        var newPassword = request.get("newPassword");
        userService.updatePassword(username, newPassword);
        return ResponseEntity.ok(Map.of("message", "Update Password Successfully"));
    }

    @PostMapping("/update-avatar")
    public ResponseEntity<Map<String, String>> updateAvatar(@RequestParam("token") String token, @RequestParam("file") MultipartFile file) {
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token"));
        }
        var username = tokenService.getUsernameFromToken(token);
        try {
            var avatarBlob = file.getBytes();
            userService.updateAvatar(username, avatarBlob);
        } catch (IOException e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage()));
        }
        return ResponseEntity.ok(Map.of("message", "Update Avatar Successfully"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> request) {
        var username = request.get("username");
        var email = userService.getEmailByUsername(username);
        if (email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Email Not Exists"));
        }
        var verificationCode = request.get("verificationCode");
        if (!redisService.getVerificationCode(email).equals(verificationCode)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Incorrect Verification Code"));
        }
        redisService.deleteVerificationCode(email);
        var password = request.get("newPassword");
        userService.updatePassword(username, password);
        return ResponseEntity.ok(Map.of("message", "Reset Password Successfully"));
    }

    @PostMapping("/get-username-from-token")
    public ResponseEntity<Map<String, String>> getUsernameFromToken(@RequestBody Map<String, String> request) {
        var token = request.get("token");
        return ResponseEntity.ok(Map.of("username", tokenService.getUsernameFromToken(token)));
    }

    @PostMapping("/get-identity-from-token")
    public ResponseEntity<Map<String, String>> getIdentityFromToken(@RequestBody Map<String, String> request) {
        var token = request.get("token");
        return ResponseEntity.ok(Map.of("identity", tokenService.getIdentityFromToken(token)));
    }
}
