package cn.com.spoc.blogservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "attention")
@NoArgsConstructor
public class AttentionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private long id;
    @Column(name = "me", columnDefinition = "VARCHAR(255)")
    private String me;
    @Column(name = "other", columnDefinition = "VARCHAR(255)")
    private String other;
    @Column(name = "attention", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean attention;

    public AttentionEntity(String me, String other) {
        this.me = me;
        this.other = other;
        this.attention = false;
    }
}
