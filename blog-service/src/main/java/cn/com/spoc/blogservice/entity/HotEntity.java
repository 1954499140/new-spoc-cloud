package cn.com.spoc.blogservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hot")
@Getter
@Setter
@NoArgsConstructor
public class HotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private long id;
    @Column(name = "username", columnDefinition = "VARCHAR(255)")
    private String username;
    @ManyToOne
    @JoinColumn(name = "blog_title", nullable = false)
    private BlogEntity blogTitle;
    @Column(name = "liked", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean liked;
    @Column(name = "favorite", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean favorite;

    public HotEntity(String userEntity, BlogEntity blogEntity) {
        this.username = userEntity;
        this.blogTitle = blogEntity;
    }
}
