package org.learning.dlearning_backend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class OtpService {
    RedisTemplate<String,Object> redisTemplate;
    public void saveOtp(String email, String otp){
        redisTemplate.opsForValue().set(email, otp, 30, TimeUnit.MINUTES);
    }
    public String getOtp(String email) {
        return (String) redisTemplate.opsForValue().get(email);
    }
    public void deleteOtp(String email) {
        redisTemplate.delete(email);
    }
}
