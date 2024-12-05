package org.learning.dlearning_backend.service.impl;

import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.constant.PredefinedRole;
import org.learning.dlearning_backend.dto.request.EmailRequest;
import org.learning.dlearning_backend.dto.request.UserCreationRequest;
import org.learning.dlearning_backend.dto.response.UserResponse;
import org.learning.dlearning_backend.entity.Role;
import org.learning.dlearning_backend.entity.User;
import org.learning.dlearning_backend.exception.AppException;
import org.learning.dlearning_backend.exception.ErrorCode;
import org.learning.dlearning_backend.mapper.UserMapper;
import org.learning.dlearning_backend.repository.RoleRepository;
import org.learning.dlearning_backend.repository.UserRepository;
import org.learning.dlearning_backend.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    UserMapper userMapper;

    @Override
    public boolean findByEmail(EmailRequest request) {
        return userRepository.existsByEmail(request.getEmail());
    }

    @Override
    public UserResponse createUser(UserCreationRequest request, String otp) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        Role role = roleRepository.findByName(PredefinedRole.USER_ROLE).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setName(request.getFirstName() + " " + request.getLastName());
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public void sendOtpRegister(EmailRequest request) throws MessagingException, UnsupportedEncodingException {


    }

}
