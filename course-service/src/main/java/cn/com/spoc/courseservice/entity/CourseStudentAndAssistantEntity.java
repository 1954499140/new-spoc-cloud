package cn.com.spoc.courseservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "course_student_assistant")
public class CourseStudentAndAssistantEntity {
    @Id
    @Column(name = "id", columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "course_uuid", columnDefinition = "CHAR(36)")
    private String courseUUID;
    @Column(name = "username", columnDefinition = "VARCHAR(255)")
    private String username;
    @Column(name = "identity", columnDefinition = "ENUM('teacher', 'assistant', 'student')")
    private String identity;

    public CourseStudentAndAssistantEntity(String courseUUID, String username, String identity) {
        this.courseUUID = courseUUID;
        this.username = username;
        this.identity = identity;
    }
}
