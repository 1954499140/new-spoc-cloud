package cn.com.spoc.courseservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
@Setter
@NoArgsConstructor
@Table(name = "homework")
public class HomeworkEntity {
    @Id
    @Column(name = "id", columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title", columnDefinition = "VARCHAR(255)")
    private String title;
    @Column(name = "content", columnDefinition = "VARCHAR(1023)")
    private String content;
    @Column(name = "created_by", columnDefinition = "VARCHAR(255)")
    private String createdBy;
    @Column(name = "created_at", columnDefinition = "DATETIME")
    private LocalDateTime createdAt;
    @Column(name = "deadline", columnDefinition = "VARCHAR(48)")
    private String deadline;
    @Column(name = "type", columnDefinition = "ENUM('success', 'warning', 'info', 'primary', 'danger')")
    private String type;
    @Column(name = "course_id", columnDefinition = "CHAR(36)")
    private String courseUUID;

    public HomeworkEntity(String title, String content, String createdBy, String deadline, String type, String courseUUID) {
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
        this.deadline = deadline;
        this.type = type;
        this.courseUUID = courseUUID;
    }
}
