package cn.com.spoc.blogservice.controller;

import cn.com.spoc.blogservice.dto.blog.BlogDTO;
import cn.com.spoc.blogservice.dto.hot.AuthorInfoDTO;
import cn.com.spoc.blogservice.service.AttentionService;
import cn.com.spoc.blogservice.service.BlogService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blog-direct")
public class BlogDirectController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private AttentionService attentionService;
    @Value("${application.blog-cover.base-url}")
    private String blogCoverBaseUrl;
    @Value("${application.blog-cover.default-url}")
    private String blogCoverDefaultUrl;

    @PostMapping("/get-blogs-list")
    public ResponseEntity<List<BlogDTO>> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlogs());
    }

    @PostMapping("/get-by-column")
    public ResponseEntity<List<BlogDTO>> getByColumn(@RequestBody Map<String, Object> request) {
        var column = (String) request.get("column");
        var blogDTOS = blogService.getColumn(column);
        if (blogDTOS == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(blogDTOS);
    }

    @PostMapping("/get-attention-info")
    public ResponseEntity<AuthorInfoDTO> getAttention(@RequestBody Map<String, Object> request) {
        var username = (String) request.get("username");
        var authorInfoDTO = attentionService.getAttentionUserInfo(username);
        if (authorInfoDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(authorInfoDTO);
    }

    @PostMapping("/get-recommend-blogs")
    public ResponseEntity<List<String>> getRecommendBlogs() {
        var blogs = blogService.getRecommendBlogs();
        if (blogs == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/blog-cover/{blogTitle}")
    public void getBlogCover(@PathVariable String blogTitle, HttpServletResponse response) {
        var cover = blogService.getBlog(blogTitle).getBlogCover();
        try {
            if (cover == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                response.setContentType("image/jpeg");
                response.setContentLength(cover.length);
                response.getOutputStream().write(cover);
                response.getOutputStream().close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @PostMapping("/get-blog-cover-url")
    public ResponseEntity<Map<String, String>> getBlogCoverUrl(@RequestBody Map<String, String> request) {
        var blogTitle = request.get("blogTitle");
        var blogEntity = blogService.getBlog(blogTitle);
        if (blogEntity.getBlogCover() == null) {
            return ResponseEntity.ok(Map.of("url", blogCoverDefaultUrl));
        }
        return ResponseEntity.ok(Map.of("url", blogCoverBaseUrl + blogTitle));
    }
}
