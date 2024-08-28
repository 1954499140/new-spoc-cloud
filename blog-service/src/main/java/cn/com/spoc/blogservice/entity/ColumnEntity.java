package cn.com.spoc.blogservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "blog_column")
@Setter
@Getter
@NoArgsConstructor
public class ColumnEntity {
    @Column(name = "cover", columnDefinition = "MEDIUMBLOB")
    private byte[] cover;
    @Id
    @Column(name = "column_name", columnDefinition = "varchar(50)")
    private String columnName;
    @OneToMany(mappedBy = "blogColumn", cascade = CascadeType.PERSIST)
    private List<BlogEntity> blogEntityLists;

    public ColumnEntity(String columnName, byte[] cover) {
        this.columnName = columnName;
        this.cover = cover;
        this.blogEntityLists = new ArrayList<>();
    }

    public ColumnEntity(String columnName) {
        this.columnName = columnName;
        this.cover = null;
        this.blogEntityLists = new ArrayList<>();
    }
}
