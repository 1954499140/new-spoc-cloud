package cn.com.spoc.blogservice.controller;

import cn.com.spoc.blogservice.dto.blog.BlogDTO;
import cn.com.spoc.blogservice.dto.blog.BlogFormDTO;
import cn.com.spoc.blogservice.dto.blog.BlogInfoDTO;
import cn.com.spoc.blogservice.dto.hot.AuthorInfoDTO;
import cn.com.spoc.blogservice.entity.BlogEntity;
import cn.com.spoc.blogservice.entity.ColumnEntity;
import cn.com.spoc.blogservice.entity.TagEntity;
import cn.com.spoc.blogservice.service.BlogService;
import cn.com.spoc.blogservice.service.HotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blog-indirect")
public class BlogIndirectController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private HotService hotService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadBlog(@ModelAttribute BlogFormDTO blogForm, @RequestPart("file") MultipartFile file) {
        try {
            var blogCover = file.getBytes();
            var tags = new ArrayList<TagEntity>();
            if (blogForm.getTags() != null) {
                for (var tag : blogForm.getTags()) {
                    var tagEntity = new TagEntity(tag);
                    tags.add(tagEntity);
                }
            }
            var column = new ColumnEntity(blogForm.getBlogColumn());
            System.out.print(blogForm.getBlogTitle());
            var blog = new BlogEntity(blogForm.getBlogTitle(), blogForm.getUsername(), column, blogCover, blogForm.getBlogContent());
            blogService.saveBlog(blog, tags);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "File Get Failed"));
        }
        return ResponseEntity.ok(Map.of("message", "Create Successfully"));
    }

    @PostMapping("/get-blog")
    public ResponseEntity<BlogInfoDTO> getBlogInfo(@RequestBody Map<String, String> request) {
        var blogTitle = request.get("blogTitle");
        var username = request.get("username");
        var blogEntity = blogService.getBlog(blogTitle);
        var hotInfo = hotService.getHotInfoByUsername(username, blogEntity);
        if (hotInfo == null) {
            hotService.saveHotEntity(username, blogTitle);
        }
        BlogInfoDTO blogInfo;
        if (blogEntity == null) {
            blogInfo = new BlogInfoDTO(null, hotInfo);
        } else {
            blogInfo = new BlogInfoDTO(blogEntity.getBlogContent(), hotInfo);
        }
        return ResponseEntity.ok(blogInfo);
    }

    @PostMapping("/change-like")
    public ResponseEntity<Map<String, String>> changeLike(@RequestBody Map<String, Object> request) {
        var blogTitle = (String) request.get("blogTitle");
        var username = (String) request.get("username");
        var blogEntity = blogService.getBlog(blogTitle);
        var isLiked = (boolean) request.get("like");
        hotService.changLike(username, blogEntity, isLiked);
        return ResponseEntity.ok(Map.of("message", "Change Success"));
    }

    @PostMapping("/change-favorite")
    public ResponseEntity<Map<String, String>> changeFavorite(@RequestBody Map<String, Object> request) {
        var blogTitle = (String) request.get("blogTitle");
        var username = (String) request.get("username");
        var blogEntity = blogService.getBlog(blogTitle);
        var favorite = (boolean) request.get("favorite");
        hotService.changeFavorite(username, blogEntity, favorite);
        return ResponseEntity.ok(Map.of("message", "Change Success"));
    }

    @PostMapping("/get-author")
    public ResponseEntity<AuthorInfoDTO> getAuthor(@RequestBody Map<String, String> request) {
        var blogTitle = request.get("blogTitle");
        var username = request.get("username");
        var authorInfoDTO = blogService.getAuthor(username, blogTitle);
        return ResponseEntity.ok(authorInfoDTO);
    }

    @PostMapping("/get-user")
    public ResponseEntity<AuthorInfoDTO> getUser(@RequestBody Map<String, String> request) {
        var username = request.get("username");
        var authorInfoDTO = blogService.getUser(username);
        return ResponseEntity.ok(authorInfoDTO);
    }

    @PostMapping("/get-attention-blogs")
    public ResponseEntity<List<BlogDTO>> getAttentionBlogs(@RequestBody Map<String, Object> request) {
        var username = (String) request.get("username");
        var blogDTOS = blogService.getAttentionBlogs(username);
        return ResponseEntity.ok(blogDTOS);
    }

    @PostMapping("/get-favorite-blogs")
    public ResponseEntity<List<BlogDTO>> getFavoriteBlogs(@RequestBody Map<String, Object> request) {
        var username = (String) request.get("username");
        var blogDTOS = blogService.getFavoriteBlogs(username);
        return ResponseEntity.ok(blogDTOS);
    }
}
