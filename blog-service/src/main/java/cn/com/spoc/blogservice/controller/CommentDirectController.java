package cn.com.spoc.blogservice.controller;

import cn.com.spoc.blogservice.dto.comment.CommentDTO;
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
@RequestMapping("/comment-direct")
public class CommentDirectController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/get-top-comment")
    public ResponseEntity<List<CommentDTO>> getAllTopComment(@RequestBody Map<String, Object> request) {
        var blogTitle = (String) request.get("blogTitle");
        var comment = commentService.getAllTopComment(blogTitle);
        if (comment == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/get-child-comment")
    public ResponseEntity<List<CommentDTO>> getChildComment(@RequestBody Map<String, Object> request) {
        var id = (Integer) request.get("parentId");
        long parentId = id.longValue();
        var blogTitle = (String) request.get("blogTitle");
        var commentDTOS = commentService.getChildComment(parentId, blogTitle);
        if (commentDTOS == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(commentDTOS);
    }
}
