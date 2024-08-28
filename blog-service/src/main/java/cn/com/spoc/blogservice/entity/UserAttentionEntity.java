package cn.com.spoc.blogservice.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_attention")
@NoArgsConstructor
public class UserAttentionEntity {
    @Id
    @Column(name = "id", columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username", columnDefinition = "VARCHAR(255)")
    private String username;
    @Column(name = "attention_name", columnDefinition = "VARCHAR(255)")
    private String attentionName;

    public UserAttentionEntity(String username, String attentionName) {
        this.username = username;
        this.attentionName = attentionName;
    }
}
