package cn.com.spoc.courseservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "course_resources")
public class CourseResourcesEntity {
    @Id
    @Column(name = "uuid", columnDefinition = "CHAR(36)")
    private String uuid;
    @Column(name = "title", columnDefinition = "VARCHAR(255)")
    private String title;
    @Column(name = "username", columnDefinition = "VARCHAR(255)")
    private String username;
    @Column(name = "course_uuid", columnDefinition = "CHAR(36)")
    private String courseId;
    @Column(name = "type", columnDefinition = "VARCHAR(96)")
    private String type;
    @Column(name = "created_at", columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    public CourseResourcesEntity(String uuid, String title, String username, String courseId, String type) {
        this.uuid = uuid;
        this.title = title;
        this.username = username;
        this.courseId = courseId;
        this.type = type;
        this.createdAt = LocalDateTime.now();
    }
}
