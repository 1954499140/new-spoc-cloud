package cn.com.spoc.userblogservice.feign;

import cn.com.spoc.userblogservice.dto.blog.BlogDTO;
import cn.com.spoc.userblogservice.dto.blog.BlogFormDTO;
import cn.com.spoc.userblogservice.dto.blog.BlogInfoDTO;
import cn.com.spoc.userblogservice.dto.comment.ChildCommentDTO;
import cn.com.spoc.userblogservice.dto.comment.CommentDTO;
import cn.com.spoc.userblogservice.dto.comment.TopCommentDTO;
import cn.com.spoc.userblogservice.dto.hot.AttentionInfoDTO;
import cn.com.spoc.userblogservice.dto.hot.AuthorInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@FeignClient(value = "blog-service")
public interface BlogServiceOpenFeign {
    @PostMapping(value = "/blog-indirect/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Map<String, String>> uploadBlog(@ModelAttribute BlogFormDTO blogForm, @RequestPart("file") MultipartFile file);

    @PostMapping(value = "/blog-indirect/get-blog")
    ResponseEntity<BlogInfoDTO> getBlogInfo(@RequestBody Map<String, String> request);

    @PostMapping(value = "/blog-indirect/change-like")
    ResponseEntity<Map<String, String>> changeLike(@RequestBody Map<String, Object> request);

    @PostMapping(value = "/blog-indirect/change-favorite")
    ResponseEntity<Map<String, String>> changeFavorite(@RequestBody Map<String, Object> request);

    @PostMapping(value = "/blog-indirect/get-author")
    ResponseEntity<AuthorInfoDTO> getAuthor(@RequestBody Map<String, String> request);

    @PostMapping(value = "/blog-indirect/get-user")
    ResponseEntity<AuthorInfoDTO> getUser(@RequestBody Map<String, String> request);

    @PostMapping(value = "/blog-indirect/get-attention-blogs")
    ResponseEntity<List<BlogDTO>> getAttentionBlogs(@RequestBody Map<String, Object> request);

    @PostMapping(value = "/blog-indirect/get-favorite-blogs")
    ResponseEntity<List<BlogDTO>> getFavoriteBlogs(@RequestBody Map<String, Object> request);

    @PostMapping(value = "/comment-indirect/post-top")
    ResponseEntity<Map<String, String>> postTopComment(@RequestBody Map<String, String> request);

    @PostMapping(value = "/comment-indirect/post-reply")
    ResponseEntity<CommentDTO> postChildComment(@RequestBody Map<String, Object> request);

    @PostMapping(value = "/get-child-comments-by-user")
    ResponseEntity<List<ChildCommentDTO>> getChildCommentsByUser(@RequestBody Map<String, Object> request);

    @PostMapping(value = "/comment-indirect/get-top-comment-by-user")
    ResponseEntity<List<TopCommentDTO>> getTopCommentByUser(@RequestBody Map<String, Object> request);

    @PostMapping(value = "/attention-indirect/change-attention")
    ResponseEntity<Map<String, String>> changeAttention(@RequestBody Map<String, Object> request);

    @PostMapping(value = "/attention-indirect/get-attention")
    ResponseEntity<Boolean> getAttention(@RequestBody Map<String, Object> request);

    @PostMapping(value = "/attention-indirect/get-my-attention")
    ResponseEntity<List<AttentionInfoDTO>> getMyAttention(@RequestBody Map<String, String> request);

    @PostMapping(value = "/attention-indirect/get-attention-me")
    ResponseEntity<List<AttentionInfoDTO>> getAttentionMe(@RequestBody Map<String, String> request);
}
