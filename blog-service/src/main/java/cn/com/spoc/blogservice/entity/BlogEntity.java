package cn.com.spoc.blogservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "blogs")
@Setter
@Getter
@NoArgsConstructor
public class BlogEntity {
    @Id
    @Column(name = "blog_title", columnDefinition = "varchar(255)")
    private String blogTitle;
    @Column(name = "author", columnDefinition = "VARCHAR(255)")
    private String author;
    @ManyToOne()
    @JoinColumn(name = "blog_column")
    private ColumnEntity blogColumn;
    @Column(name = "blog_cover", columnDefinition = "MEDIUMBLOB")
    private byte[] blogCover;
    @Column(name = "blog_time", columnDefinition = "VARCHAR(20)")
    private String blogDate;
    @Column(name = "blog_content", columnDefinition = "TEXT")
    private String blogContent;
    @ManyToMany
    @JoinTable(
            name = "blogs_tags",
            joinColumns = @JoinColumn(name = "blog_title"),
            inverseJoinColumns = @JoinColumn(name = "tag_name")
    )
    private List<TagEntity> tags;

    public BlogEntity(String blogTitle, String author, ColumnEntity blgColumn, byte[] blogCover, String blogContent) {
        this.blogTitle = blogTitle;
        this.author = author;
        this.blogColumn = blgColumn;
        this.blogCover = blogCover;
        this.tags = new ArrayList<>();
        this.blogDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.blogContent = blogContent;
    }
}
