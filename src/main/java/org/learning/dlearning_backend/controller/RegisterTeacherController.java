package org.learning.dlearning_backend.controller;

import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.dto.request.UserRegisterTeacherRequest;
import org.learning.dlearning_backend.dto.response.ApiResponse;
import org.learning.dlearning_backend.dto.response.UserRegisterTeacherResponse;
import org.learning.dlearning_backend.service.impl.RegisterTeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1")
@Slf4j
public class RegisterTeacherController {
    RegisterTeacherService registerTeacherService;
    @GetMapping("/registration-teachers")
    public ApiResponse<List<UserRegisterTeacherResponse>> getAll(){
        return ApiResponse.<List<UserRegisterTeacherResponse>>builder()
                .code(HttpStatus.OK.value())
                .result(registerTeacherService.getAll())
                .build();
    }

    @PostMapping("/register-teacher")
    public ApiResponse<UserRegisterTeacherResponse> registerTeacher(
          @RequestPart("request") UserRegisterTeacherRequest request,
          @RequestPart("cv") MultipartFile cv,
          @RequestPart("certificate")  MultipartFile certificate
    ) throws URISyntaxException, IOException {

        var result = registerTeacherService.registerTeacher(request, cv, certificate);

        return ApiResponse.<UserRegisterTeacherResponse>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .build();
    }

    @PostMapping("/save-teacher/{id}")
    public ApiResponse<UserRegisterTeacherResponse> saveTeacher (@PathVariable @Min(1) Long id) {
        var result = registerTeacherService.saveTeacher(id);

        return ApiResponse.<UserRegisterTeacherResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Approve Teacher Successfully")
                .result(result)
                .build();
    }
    @PostMapping("/reject-teacher/{id}")
    ApiResponse<UserRegisterTeacherResponse> rejectTeacher(@PathVariable @Min(1) Long id) {
        var result = registerTeacherService.rejectTeacher(id);

        return ApiResponse.<UserRegisterTeacherResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Reject Successfully")
                .result(result)
                .build();
    }
}
