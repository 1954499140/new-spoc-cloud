package cn.com.spoc.userblogservice.controller;

import cn.com.spoc.userblogservice.dto.hot.AttentionInfoDTO;
import cn.com.spoc.userblogservice.feign.BlogServiceOpenFeign;
import cn.com.spoc.userblogservice.feign.UserServiceOpenFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attention")
public class AttentionController {
    @Autowired
    private UserServiceOpenFeign userFeign;
    @Autowired
    private BlogServiceOpenFeign blogFeign;

    @PostMapping("/change-attention")
    public ResponseEntity<Map<String, String>> changeAttention(@RequestBody Map<String, Object> request) {
        var token = (String) request.get("token");
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token"));
        }
        var username = userFeign.getUsernameFromToken(token);
        return blogFeign.changeAttention(Map.of(
                "me", username,
                "other", request.get("other"),
                "attention", request.get("attention")
        ));
    }

    @PostMapping("/get-attention")
    public ResponseEntity<Boolean> getAttention(@RequestBody Map<String, Object> request) {
        var token = (String) request.get("token");
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
        var username = userFeign.getUsernameFromToken(token);
        return blogFeign.getAttention(Map.of(
                "me", username,
                "other", request.get("other")
        ));
    }

    @PostMapping("/get-my-attention")
    public ResponseEntity<List<AttentionInfoDTO>> getMyAttention(@RequestBody Map<String, String> request) {
        var token = request.get("token");
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        var username = userFeign.getUsernameFromToken(token);
        return blogFeign.getMyAttention(Map.of(
                "me", username
        ));
    }

    @PostMapping("/get-attention-me")
    public ResponseEntity<List<AttentionInfoDTO>> getAttentionMe(@RequestBody Map<String, String> request) {
        var token = request.get("token");
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        var username = userFeign.getUsernameFromToken(token);
        return blogFeign.getAttentionMe(Map.of(
                "other", username
        ));
    }
}
