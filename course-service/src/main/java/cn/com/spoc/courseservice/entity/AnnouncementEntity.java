package cn.com.spoc.courseservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "announcements")
public class AnnouncementEntity {
    @Id
    @Column(name = "id", columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username", columnDefinition = "VARCHAR(255)")
    private String username;
    @Column(name = "course_uuid", columnDefinition = "CHAR(36)")
    private String courseUUID;
    @Column(name = "title", columnDefinition = "VARCHAR(255)")
    private String title;
    @Column(name = "content", columnDefinition = "VARCHAR(1023)")
    private String content;
    @Column(name = "created_at", columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    public AnnouncementEntity(String username, String title, String content, String courseUUID) {
        this.username = username;
        this.courseUUID = courseUUID;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}
