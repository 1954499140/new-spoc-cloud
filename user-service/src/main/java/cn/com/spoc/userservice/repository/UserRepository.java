package cn.com.spoc.userservice.repository;

import cn.com.spoc.userservice.dto.UserInfoDTO;
import cn.com.spoc.userservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    @Query("SELECT user.password FROM UserEntity user WHERE user.username = :username")
    String findPasswordByUsername(@Param("username") String username);

    @Query("SELECT user.identity FROM UserEntity user WHERE user.username = :username")
    String findIdentityByUsername(@Param("username") String username);

    @Query("SELECT user.avatar FROM UserEntity user WHERE user.username = :username")
    byte[] findAvatarByUsername(@Param("username") String username);

    @Query("SELECT new cn.com.spoc.userservice.dto.UserInfoDTO(user.username, user.email, user.identity, user.signature) FROM UserEntity user WHERE user.username = :username")
    UserInfoDTO findUserInfoByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity user SET user.email = :email, user.signature = :signature WHERE user.username = :username")
    void updateUserInfo(@Param("email") String email, @Param("signature") String signature, @Param("username") String username);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity user SET user.password = :password WHERE user.username = :username")
    void updatePassword(@Param("password") String password, @Param("username") String username);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity user SET user.avatar = :avatar WHERE user.username = :username")
    void updateAvatar(@Param("avatar") byte[] avatar, @Param("username") String username);

    @Query("SELECT user.email FROM UserEntity user WHERE user.username = :username")
    String findEmailByUsername(@Param("username") String username);
}
