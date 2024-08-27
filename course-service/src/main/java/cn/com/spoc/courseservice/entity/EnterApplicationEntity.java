package cn.com.spoc.courseservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Setter
@NoArgsConstructor
@Table(name = "enter_applications")
public class EnterApplicationEntity {
    @Id
    @Column(name = "id", columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username", columnDefinition = "VARCHAR(255)")
    private String username;
    @Column(name = "identity", columnDefinition = "ENUM('teacher', 'assistant', 'student')")
    private String identity;
    @Column(name = "course_uuid", columnDefinition = "CHAR(36)")
    private String courseUUID;
    @Column(name = "remark", columnDefinition = "VARCHAR(255)")
    private String remark;

    public EnterApplicationEntity(String username, String identity, String courseUUID, String remark) {
        this.username = username;
        this.identity = identity;
        this.courseUUID = courseUUID;
        this.remark = remark;
    }
}
