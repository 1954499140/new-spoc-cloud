package cn.com.spoc.blogservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private long id;
    @Column(name = "user", columnDefinition = "VARCHAR(255)")
    private String user;
    @ManyToOne()
    private BlogEntity blogEntity;
    @Column(name = "comment", columnDefinition = "VARCHAR(1024)")
    private String content;
    @Column(name = "create_time", columnDefinition = "DATE")
    private String createTime;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CommentEntity parentComment;
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> child;

    public CommentEntity(String user, BlogEntity blogEntity, String content, CommentEntity parentComment) {
        this.user = user;
        this.blogEntity = blogEntity;
        this.content = content;
        this.parentComment = parentComment;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.createTime = now.format(formatter);
        this.child = new ArrayList<>();

    }

    public CommentEntity() {
    }
}
