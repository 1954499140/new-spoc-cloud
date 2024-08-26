package cn.com.spoc.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveVerificationCode(String email, String code) {
        redisTemplate.opsForValue().set(email, code, 5, TimeUnit.MINUTES);
    }

    public String getVerificationCode(String email) {
        return (String) redisTemplate.opsForValue().get(email);
    }

    public void deleteVerificationCode(String email) {
        redisTemplate.delete(email);
    }
}
