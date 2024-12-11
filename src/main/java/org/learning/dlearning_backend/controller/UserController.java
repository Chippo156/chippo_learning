package org.learning.dlearning_backend.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.learning.dlearning_backend.dto.request.EmailRequest;
import org.learning.dlearning_backend.dto.request.PasswordCreationRequest;
import org.learning.dlearning_backend.dto.request.UserCreationRequest;
import org.learning.dlearning_backend.dto.request.VerifyOtpRequest;
import org.learning.dlearning_backend.dto.response.ApiResponse;
import org.learning.dlearning_backend.dto.response.GetPointsCurrentLogin;
import org.learning.dlearning_backend.dto.response.UserResponse;
import org.learning.dlearning_backend.dto.response.VerifyOtpResponse;
import org.learning.dlearning_backend.service.UserService;
import org.learning.dlearning_backend.service.impl.CloudinaryService;
import org.learning.dlearning_backend.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("${api-prefix}")
public class UserController {
    UserServiceImpl userService;
    CloudinaryService cloudinaryService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request,
                                                @RequestParam String otp) {
        var result = userService.createUser(request, otp);
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.CREATED.value())
                .result(result)
                .build();
    }
    @PostMapping("/check-exists-user")
    ApiResponse<Boolean> checkExistsUser(@RequestBody EmailRequest request){
        var result = userService.findByEmail(request);
        return ApiResponse.<Boolean>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .build();
    }
    @PostMapping("/send-otp-register")
    ApiResponse<Void> sendOtpRegister(@RequestBody EmailRequest request)
            throws MessagingException, UnsupportedEncodingException {
        userService.sendOtpRegister(request);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Send Otp Successfully")
                .build();
    }

    @PostMapping("/verify-otp")
    public ApiResponse<VerifyOtpResponse> verifyOtp(@RequestBody VerifyOtpRequest request) {
        var result = userService.verifyOtp(request);
        return ApiResponse.<VerifyOtpResponse>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .message("Verify Otp Successfully")
                .build();
    }

    @PostMapping("/reset-password")
    ApiResponse<Void> resetPassword(@RequestBody PasswordCreationRequest request) {
        userService.resetPassword(request);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Reset Password Successfully")
                .build();
    }
    @GetMapping("/users")
    public ApiResponse<List<UserResponse>> getAllUsers() {
        var result = userService.getAllUsers();
        return ApiResponse.<List<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .build();
    }
    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        var result = userService.getMyInfo();
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .build();
    }
    @GetMapping("/get-avatar")
    ApiResponse<String> getAvatar(){
        String avatar = cloudinaryService.getImage();
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .result(avatar)
                .build();
    }
    @GetMapping("/get-points-user-current")
    ApiResponse<GetPointsCurrentLogin> getPointsUserLogin(){
        return ApiResponse.<GetPointsCurrentLogin>builder()
                .code(HttpStatus.OK.value())
                .result(userService.getPointsCurrentLogin())
                .build();
    }
}
