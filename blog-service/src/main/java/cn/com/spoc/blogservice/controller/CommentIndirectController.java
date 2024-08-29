package cn.com.spoc.blogservice.controller;

import cn.com.spoc.blogservice.dto.comment.ChildCommentDTO;
import cn.com.spoc.blogservice.dto.comment.CommentDTO;
import cn.com.spoc.blogservice.dto.comment.TopCommentDTO;
import cn.com.spoc.blogservice.service.CommentService;
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
@RequestMapping("/comment-indirect")
public class CommentIndirectController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/post-top")
    public ResponseEntity<Map<String, String>> postTopComment(@RequestBody Map<String, String> request) {
        String blogTitle = request.get("blogTitle");
        String authorName = request.get("username");
        String content = request.get("content");
        commentService.postTopComment(content, authorName, blogTitle);
        return ResponseEntity.ok(Map.of("ok", "ok"));
    }

    @PostMapping("/post-reply")
    public ResponseEntity<CommentDTO> postChildComment(@RequestBody Map<String, Object> request) {
        var blogTitle = (String) request.get("blogTitle");
        var authorName = (String) request.get("username");
        var content = (String) request.get("content");
        var id = (Integer) request.get("parentId");
        long parentId = id.longValue();
        return ResponseEntity.ok(commentService.PostChildComment(content, authorName, blogTitle, parentId));
    }

    @PostMapping("/get-child-comments-by-user")
    public ResponseEntity<List<ChildCommentDTO>> getChildCommentsByUser(@RequestBody Map<String, Object> request) {
        var username = (String) request.get("username");
        var commentDTOS = commentService.getChildCommentByUser(username);
        if (commentDTOS == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(commentDTOS);
    }

    @PostMapping("/get-top-comment-by-user")
    public ResponseEntity<List<TopCommentDTO>> getTopCommentByUser(@RequestBody Map<String, Object> request) {
        var username = (String) request.get("username");
        var topCommentDTOS = commentService.getTopCommentByUser(username);
        if (topCommentDTOS == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(topCommentDTOS);
    }
}
