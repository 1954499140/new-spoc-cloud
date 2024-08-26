package cn.com.spoc.userservice.service;

import cn.com.spoc.userservice.dto.UserInfoDTO;
import cn.com.spoc.userservice.entity.UserEntity;
import cn.com.spoc.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean existsUsername(String username) {
        return userRepository.existsById(username);
    }

    public void registerUser(String username, String password, String email, String identity) {
        var user = new UserEntity(username, passwordEncoder.encode(password), email, identity);
        userRepository.save(user);
    }

    public boolean isPasswordMatch(String username, String password) {
        var correctPassword = userRepository.findPasswordByUsername(username);
        return passwordEncoder.matches(password, correctPassword);
    }

    public boolean existsAvatar(String username) {
        return userRepository.findAvatarByUsername(username) != null;
    }

    public byte[] getAvatar(String username) {
        return userRepository.findAvatarByUsername(username);
    }

    public UserEntity getUserById(String authorName) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(authorName);
        return optionalUserEntity.orElse(null);
    }

    public UserInfoDTO getUserInfo(String username) {
        return userRepository.findUserInfoByUsername(username);
    }

    public void editUserInfo(String username, String email, String signature) {
        userRepository.updateUserInfo(email, signature, username);
    }

    public void updatePassword(String username, String password) {
        var encodedPassword = passwordEncoder.encode(password);
        userRepository.updatePassword(encodedPassword, username);
    }

    public void updateAvatar(String username, byte[] avatar) {
        userRepository.updateAvatar(avatar, username);
    }

    public String getEmailByUsername(String username) {
        return userRepository.findEmailByUsername(username);
    }
}
