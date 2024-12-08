package org.learning.dlearning_backend.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.dto.request.UserProfileRequest;
import org.learning.dlearning_backend.dto.response.UserProfileResponse;
import org.learning.dlearning_backend.entity.User;
import org.learning.dlearning_backend.exception.AppException;
import org.learning.dlearning_backend.exception.ErrorCode;
import org.learning.dlearning_backend.mapper.ProfileMapper;
import org.learning.dlearning_backend.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProfileService {
    UserRepository userRepository;
    ProfileMapper profileMapper;

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public void updateProfile(UserProfileRequest request) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String email = securityContext.getAuthentication().getName();
        if (email != null) {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

            profileMapper.updateUser(request, user);
            if (request.getFirstName() != null && request.getLastName() != null) {
                user.setName(request.getFirstName() + " " + request.getLastName());
            }
            userRepository.save(user);
            log.info("User profile updated successfully for user with email: {}", email);

        }
    }

    @PreAuthorize("isAuthenticated()")
    public UserProfileResponse getInfoProfile(){
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(()
                -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return profileMapper.getInfoUser(user);
    }



}
