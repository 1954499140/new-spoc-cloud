package cn.com.spoc.blogservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tags")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity {
    @Id
    @Column(name = "tag_name", columnDefinition = "VARCHAR(50)", nullable = false)
    private String tagName;
}
