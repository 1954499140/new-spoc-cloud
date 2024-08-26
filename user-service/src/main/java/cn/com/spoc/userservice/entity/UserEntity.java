package cn.com.spoc.userservice.entity;

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
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "username", columnDefinition = "VARCHAR(255)")
    private String username;
    @Column(name = "password", columnDefinition = "VARCHAR(255)")
    private String password;
    @Column(name = "email", columnDefinition = "VARCHAR(255)")
    private String email;
    @Column(name = "identity", columnDefinition = "ENUM('student', 'teacher', 'assistant')")
    private String identity;
    @Column(name = "signature", columnDefinition = "VARCHAR(255)")
    private String signature;
    @Column(name = "avatar", columnDefinition = "MEDIUMBLOB")
    private byte[] avatar;

    public UserEntity(String username, String password, String email, String identity) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.identity = identity;
        this.signature = "";
        this.avatar = null;
    }
}
