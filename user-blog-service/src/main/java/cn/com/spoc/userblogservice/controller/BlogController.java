package cn.com.spoc.userblogservice.controller;

import cn.com.spoc.userblogservice.dto.blog.BlogDTO;
import cn.com.spoc.userblogservice.dto.blog.BlogFormDTO;
import cn.com.spoc.userblogservice.dto.blog.BlogInfoDTO;
import cn.com.spoc.userblogservice.dto.blog.PreBlogFormDTO;
import cn.com.spoc.userblogservice.dto.hot.AuthorInfoDTO;
import cn.com.spoc.userblogservice.feign.BlogServiceOpenFeign;
import cn.com.spoc.userblogservice.feign.UserServiceOpenFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogServiceOpenFeign blogFeign;
    @Autowired
    private UserServiceOpenFeign userFeign;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadBlog(@ModelAttribute PreBlogFormDTO blogForm, @RequestPart("file") MultipartFile file) {
        var token = blogForm.getToken();
        var username = userFeign.getUsernameFromToken(token);
        var request = new BlogFormDTO(blogForm.getBlogTitle(), blogForm.getBlogColumn(), blogForm.getBlogContent(), username, blogForm.getTags());
        return blogFeign.uploadBlog(request, file);
    }

    @PostMapping("/get-blog")
    public ResponseEntity<BlogInfoDTO> getBlogInfo(@RequestBody Map<String, String> request) {
        var token = request.get("token");
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        var username = userFeign.getUsernameFromToken(token);
        var blogTitle = request.get("blogTitle");
        return blogFeign.getBlogInfo(Map.of(
                "username", username,
                "blogTitle", blogTitle
        ));
    }

    @PostMapping("/change-like")
    public ResponseEntity<Map<String, String>> changeLike(@RequestBody Map<String, Object> request) {
        var token = (String) request.get("token");
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token"));
        }
        var username = userFeign.getUsernameFromToken(token);
        var blogTitle = (String) request.get("blogTitle");
        return blogFeign.changeLike(Map.of(
                "username", username,
                "blogTitle", blogTitle,
                "like", request.get("like")
        ));
    }

    @PostMapping("/change-favorite")
    public ResponseEntity<Map<String, String>> changeFavorite(@RequestBody Map<String, Object> request) {
        var token = (String) request.get("token");
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token"));
        }
        var username = userFeign.getUsernameFromToken(token);
        var blogTitle = (String) request.get("blogTitle");
        var favorite = request.get("favorite");
        return blogFeign.changeFavorite(Map.of(
                "username", username,
                "blogTitle", blogTitle,
                "favorite", favorite
        ));
    }

    @PostMapping("/get-author")
    public ResponseEntity<AuthorInfoDTO> getAuthor(@RequestBody Map<String, String> request) {
        var token = request.get("token");
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        var username = userFeign.getUsernameFromToken(token);
        var blogTitle = request.get("blogTitle");
        return blogFeign.getAuthor(Map.of(
                "username", username,
                "blogTitle", blogTitle
        ));
    }

    @PostMapping("/get-user")
    public ResponseEntity<AuthorInfoDTO> getUser(@RequestBody Map<String, String> request) {
        var token = request.get("token");
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        var username = userFeign.getUsernameFromToken(token);
        return blogFeign.getUser(Map.of(
                "username", username
        ));
    }

    @PostMapping("/get-attention-blogs")
    public ResponseEntity<List<BlogDTO>> getAttentionBlogs(@RequestBody Map<String, Object> request) {
        var token = (String) request.get("token");
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        var username = userFeign.getUsernameFromToken(token);
        return blogFeign.getAttentionBlogs(Map.of(
                "username", username
        ));
    }

    @PostMapping("/get-favorite-blogs")
    public ResponseEntity<List<BlogDTO>> getFavoriteBlogs(@RequestBody Map<String, Object> request) {
        var token = (String) request.get("token");
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        var username = userFeign.getUsernameFromToken(token);
        return blogFeign.getFavoriteBlogs(Map.of(
                "username", username
        ));
    }
}
