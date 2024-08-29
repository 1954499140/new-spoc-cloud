package cn.com.spoc.userblogservice.controller;

import cn.com.spoc.userblogservice.dto.comment.ChildCommentDTO;
import cn.com.spoc.userblogservice.dto.comment.CommentDTO;
import cn.com.spoc.userblogservice.dto.comment.TopCommentDTO;
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
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private UserServiceOpenFeign userFeign;
    @Autowired
    private BlogServiceOpenFeign blogFeign;

    @PostMapping("/post-top")
    public ResponseEntity<Map<String, String>> postTopComment(@RequestBody Map<String, String> request) {
        var token = request.get("token");
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token"));
        }
        var username = userFeign.getUsernameFromToken(token);
        return blogFeign.postTopComment(Map.of(
                "username", username,
                "blogTitle", request.get("blogTitle"),
                "content", request.get("content")
        ));
    }

    @PostMapping("/post-reply")
    public ResponseEntity<CommentDTO> postChildComment(@RequestBody Map<String, Object> request) {
        var token = (String) request.get("token");
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        var username = userFeign.getUsernameFromToken(token);
        return blogFeign.postChildComment(Map.of(
                "username", username,
                "blogTitle", request.get("blogTitle"),
                "content", request.get("content"),
                "parentId", request.get("parentId")
        ));
    }

    @PostMapping("/get-child-comments-by-user")
    public ResponseEntity<List<ChildCommentDTO>> getChildCommentsByUser(@RequestBody Map<String, Object> request) {
        var token = (String) request.get("token");
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        var username = userFeign.getUsernameFromToken(token);
        return blogFeign.getChildCommentsByUser(Map.of(
                "username", username
        ));
    }

    @PostMapping("/get-top-comment-by-user")
    public ResponseEntity<List<TopCommentDTO>> getTopCommentByUser(@RequestBody Map<String, Object> request) {
        var token = (String) request.get("token");
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        var username = userFeign.getUsernameFromToken(token);
        return blogFeign.getTopCommentByUser(Map.of(
                "username", username
        ));
    }
}
