package org.learning.dlearning_backend.service;

import jakarta.mail.MessagingException;
import org.learning.dlearning_backend.dto.request.EmailRequest;
import org.learning.dlearning_backend.dto.request.UserCreationRequest;
import org.learning.dlearning_backend.dto.response.UserResponse;

import java.io.UnsupportedEncodingException;

public interface UserService {
    boolean findByEmail(EmailRequest request);

    UserResponse createUser(UserCreationRequest request, String otp);

    void sendOtpRegister(EmailRequest request) throws MessagingException, UnsupportedEncodingException;
}
