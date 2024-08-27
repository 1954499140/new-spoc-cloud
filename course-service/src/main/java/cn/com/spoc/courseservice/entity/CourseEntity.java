package cn.com.spoc.courseservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "courses")
public class CourseEntity {
    @Id
    @Column(name = "uuid", columnDefinition = "VARCHAR(36)")
    private String uuid;
    @Column(name = "name", columnDefinition = "VARCHAR(255)")
    private String name;
    @Column(name = "teacher", columnDefinition = "VARCHAR(255)")
    private String teacher;
    @Column(name = "teacher_name", columnDefinition = "VARCHAR(255)")
    private String teacherName;
    @Column(name = "primary_classification", columnDefinition = "ENUM('基础课程', '通修课程', '专业课程')")
    private String primaryClassification;
    @Column(name = "secondary_classification", columnDefinition = "ENUM('数学与自然科学类', '工程基础类', '外语类', '思政类', '军理类', '体育类', '素质教育实践必修课', '素质教育理论必修课', '素质教育通识限修课', '核心专业类', '一般专业类')")
    private String secondaryClassification;
    @Column(name = "description", columnDefinition = "VARCHAR(1023)")
    private String description;
    @Column(name = "cover", columnDefinition = "MEDIUMBLOB")
    private byte[] cover;

    public CourseEntity(String uuid, String name, String teacher, String teacherName, String description, String primaryClassification, String secondaryClassification, byte[] cover) {
        this.uuid = uuid;
        this.name = name;
        this.teacher = teacher;
        this.teacherName = teacherName;
        this.description = description;
        this.primaryClassification = primaryClassification;
        this.secondaryClassification = secondaryClassification;
        this.cover = cover;
    }
}
