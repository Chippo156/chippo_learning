package org.learning.dlearning_backend.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.dto.request.UserCreationRequest;
import org.learning.dlearning_backend.dto.request.UserProfileRequest;
import org.learning.dlearning_backend.dto.response.ApiResponse;
import org.learning.dlearning_backend.dto.response.UserProfileResponse;
import org.learning.dlearning_backend.service.impl.CloudinaryService;
import org.learning.dlearning_backend.service.impl.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1")
@Slf4j
public class ProfileController {
    ProfileService profileService;
    CloudinaryService cloudinaryService;
    @PutMapping("/update-profile")
    ApiResponse<Void> updateProfile(@RequestBody @Valid UserProfileRequest request){
        profileService.updateProfile(request);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Profile updated successfully")
                .build();
    }

    @GetMapping("/info-user")
    ApiResponse<UserProfileResponse> getUserProfile(){
        var result = profileService.getInfoProfile();
        return ApiResponse.<UserProfileResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Profile info successfully")
                .result(result)
                .build();
    }
    @PostMapping("/update-avatar")
    ApiResponse<String> updateAvatar(@RequestParam("file") MultipartFile file) {
        SecurityContext context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        String url = cloudinaryService.uploadImage(file);

        cloudinaryService.updateImage(url, email);

        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Profile updated successfully")
                .build();
    }
}
