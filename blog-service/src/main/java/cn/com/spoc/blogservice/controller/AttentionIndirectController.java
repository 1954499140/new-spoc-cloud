package cn.com.spoc.blogservice.controller;

import cn.com.spoc.blogservice.dto.hot.AttentionInfoDTO;
import cn.com.spoc.blogservice.service.AttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attention-indirect")
public class AttentionIndirectController {
    @Autowired
    private AttentionService attentionService;

    @PostMapping("/change-attention")
    public ResponseEntity<Map<String, String>> changeAttention(@RequestBody Map<String, Object> request) {
        var me = (String) request.get("me");
        var other = (String) request.get("other");
        boolean attention = (boolean) request.get("attention");
        attentionService.change(me, other, attention);
        return ResponseEntity.ok(Map.of("message", "Change Attention Successfully"));
    }

    @PostMapping("/get-attention")
    public ResponseEntity<Boolean> getAttention(@RequestBody Map<String, Object> request) {
        var other = (String) request.get("other");
        var me = (String) request.get("me");
        return ResponseEntity.ok(attentionService.getIsAttention(me, other));
    }

    @PostMapping("/get-my-attention")
    public ResponseEntity<List<AttentionInfoDTO>> getMyAttention(@RequestBody Map<String, String> request) {
        var me = request.get("me");
        return ResponseEntity.ok(attentionService.getMyAttention(me));
    }

    @PostMapping("/get-attention-me")
    public ResponseEntity<List<AttentionInfoDTO>> getAttentionMe(@RequestBody Map<String, String> request) {
        String other = request.get("other");
        return ResponseEntity.ok(attentionService.getMyAttention(other));
    }
}
