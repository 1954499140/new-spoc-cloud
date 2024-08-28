package cn.com.spoc.blogservice.service;

import cn.com.spoc.blogservice.dto.comment.ChildCommentDTO;
import cn.com.spoc.blogservice.dto.comment.CommentDTO;
import cn.com.spoc.blogservice.dto.comment.TopCommentDTO;
import cn.com.spoc.blogservice.entity.CommentEntity;
import cn.com.spoc.blogservice.repository.BlogRepository;
import cn.com.spoc.blogservice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BlogRepository blogRepository;

    public List<CommentDTO> getAllTopComment(String blogTitle) {
        var blogEntity = blogRepository.findBlogEntitiesByBlogTitle(blogTitle);
        var allComments = commentRepository.getAllCommentsByBlog(blogEntity);
        var commentDTOMap = new HashMap<Long, CommentDTO>();
        var topLevelComments = new ArrayList<CommentDTO>();
        for (var comment : allComments) {
            var content = comment.getContent();
            var authorName = comment.getUser();
            var time = comment.getCreateTime();
            var id = comment.getId();
            var dto = new CommentDTO(id, authorName, null, content, time);
            commentDTOMap.put(comment.getId(), dto);
            if (comment.getParentComment() == null) {
                topLevelComments.add(dto);
            }
        }
        for (var comment : allComments) {
            if (comment.getParentComment() != null) {
                var parentDTO = commentDTOMap.get(comment.getParentComment().getId());
                var childDTO = commentDTOMap.get(comment.getId());
                if (parentDTO != null && childDTO != null) {
                    if (parentDTO.getChild() == null) {
                        parentDTO.setChild(new ArrayList<>());
                    }
                    parentDTO.getChild().add(childDTO);
                }
            }
        }
        return topLevelComments;
    }

    public void postTopComment(String content, String authorName, String blogTitle) {
        var blogEntity = blogRepository.findBlogEntitiesByBlogTitle(blogTitle);
        var commentEntity = new CommentEntity(authorName, blogEntity, content, null);
        commentRepository.save(commentEntity);
    }

    public CommentDTO PostChildComment(String content, String authorName, String blogTitle, long ParentId) {
        var blogEntity = blogRepository.findBlogEntitiesByBlogTitle(blogTitle);
        var parentComment = commentRepository.getCommentById(ParentId);
        var commentEntity = new CommentEntity(authorName, blogEntity, content, parentComment);
        var comment = commentRepository.save(commentEntity);
        return new CommentDTO(
                comment.getId(),
                comment.getUser(),
                null,
                comment.getContent(),
                comment.getCreateTime()
        );
    }

    public List<CommentDTO> getChildComment(long parentId, String blogTitle) {
        var blogEntity = blogRepository.findBlogEntitiesByBlogTitle(blogTitle);
        var allComments = commentRepository.getAllCommentsByBlog(blogEntity);
        var commentDTOMap = new HashMap<Long, CommentDTO>();
        var topLevelComments = new ArrayList<CommentDTO>();
        for (var comment : allComments) {
            var content = comment.getContent();
            var authorName = comment.getUser();
            var time = comment.getCreateTime();
            var id = comment.getId();
            var dto = new CommentDTO(id, authorName, null, content, time);
            commentDTOMap.put(comment.getId(), dto);
            if (comment.getParentComment() == null) {
                topLevelComments.add(dto);
            }
        }
        System.out.println(topLevelComments);
        for (var comment : allComments) {
            if (comment.getParentComment() != null) {
                var parentDTO = commentDTOMap.get(comment.getParentComment().getId());
                var childDTO = commentDTOMap.get(comment.getId());
                if (parentDTO != null && childDTO != null) {
                    if (parentDTO.getChild() == null) {
                        parentDTO.setChild(new ArrayList<>());
                    }
                    parentDTO.getChild().add(childDTO);
                }
            }
        }
        var parentDto = commentDTOMap.get(parentId);
        return parentDto.getChild();
    }

    public List<ChildCommentDTO> getChildCommentByUser(String username) {
        var commentEntities = commentRepository.getCommentToUser(username);
        var childCommentDTOS = new ArrayList<ChildCommentDTO>();
        for (var comment : commentEntities) {
            var time = comment.getCreateTime();
            var authorName = comment.getUser();
            var myComment = comment.getContent();
            var parentComment = comment.getParentComment().getContent();
            var childCommentDTO = new ChildCommentDTO(time, parentComment, myComment, authorName);
            childCommentDTOS.add(childCommentDTO);
        }
        return childCommentDTOS;
    }

    public List<TopCommentDTO> getTopCommentByUser(String username) {
        var commentEntities = commentRepository.getTopCommentByUser(username);
        var topCommentDTOS = new ArrayList<TopCommentDTO>();
        for (var comment : commentEntities) {
            var time = comment.getCreateTime();
            var authorName = comment.getUser();
            var blogTitle = comment.getBlogEntity().getBlogTitle();
            var content = comment.getContent();
            var topCommentDTO = new TopCommentDTO(authorName, content, blogTitle, time);
            topCommentDTOS.add(topCommentDTO);
        }
        return topCommentDTOS;
    }
}
