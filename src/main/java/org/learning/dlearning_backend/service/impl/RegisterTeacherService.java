package org.learning.dlearning_backend.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.common.RegistrationStatus;
import org.learning.dlearning_backend.constant.PredefinedRole;
import org.learning.dlearning_backend.dto.request.UserRegisterTeacherRequest;
import org.learning.dlearning_backend.dto.response.UserRegisterTeacherResponse;
import org.learning.dlearning_backend.entity.Role;
import org.learning.dlearning_backend.entity.User;
import org.learning.dlearning_backend.exception.AppException;
import org.learning.dlearning_backend.exception.ErrorCode;
import org.learning.dlearning_backend.mapper.RegisterTeacherMapper;
import org.learning.dlearning_backend.repository.RoleRepository;
import org.learning.dlearning_backend.repository.UserRepository;
import org.learning.dlearning_backend.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RegisterTeacherService {

    RegisterTeacherMapper mapper;
    UserRepository userRepository;
    RoleRepository roleRepository;
    NotificationService notificationService;
    FileService fileService;

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserRegisterTeacherResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .filter(user ->
                        user.getRegistrationStatus() != null &&
                                user.getRegistrationStatus().equals(RegistrationStatus.PENDING) &&
                                user.getRole() != null &&
                                user.getRole().getName() != null &&
                                user.getRole().getName().equals(PredefinedRole.USER_ROLE))
                .map(mapper::toTeacherResponse)
                .toList();
    }

    @Transactional
    @PreAuthorize("hasAuthority('USER') and isAuthenticated()")
    public UserRegisterTeacherResponse registerTeacher(UserRegisterTeacherRequest request,
                                                       MultipartFile cv, MultipartFile certificate) throws URISyntaxException, IOException {
        String email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_INVALID));
        User userCurrent = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (userCurrent.getRegistrationStatus() == null || userCurrent.getRegistrationStatus().equals(RegistrationStatus.REJECTED)) {
            String cvFileName = fileService.store(cv, "upload");

            String certificateFileName = fileService.store(certificate, "upload");
            log.info("cv {}", cvFileName);
            log.info("certificateFileName{}", certificateFileName);
            request.setCvUrl("/upload/" + cvFileName);
            request.setCertificate("/upload/" + certificateFileName);

            mapper.toUpdateTeacher(request, userCurrent);
            userCurrent.setRegistrationStatus(RegistrationStatus.PENDING);
            userRepository.save(userCurrent);

            String message = "A new teacher application has been submitted.";
            String title = "New Teacher Registration";
            String url = "/admin/teacher-applications";

            List<User> userAdmin = userRepository.findByRoleName(PredefinedRole.ADMIN_ROLE);
            userAdmin.forEach(admin -> notificationService.createNotification(admin, userCurrent, message, title, url));

            return mapper.toTeacherResponse(userCurrent);
        }
        throw new AppException(ErrorCode.REGISTER_TEACHER_INVALID);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN') and isAuthenticated()")
    public UserRegisterTeacherResponse saveTeacher(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        String roleName = user.getRole().getName();
        Role role = roleRepository.findByName(PredefinedRole.TEACHER_ROLE)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

        if (user.getRegistrationStatus().equals(RegistrationStatus.PENDING) && roleName.equals(PredefinedRole.USER_ROLE)) {
            user.setRole(role);
            user.setRegistrationStatus(RegistrationStatus.APPROVED);
            userRepository.save(user);

            String message = "Your teacher application has been approved.";
            String title = "Teacher Registration Approved";
            String url = "/teacher";
            List<User> userAdmin = userRepository.findByRoleName(PredefinedRole.ADMIN_ROLE);
            userAdmin.forEach(admin -> notificationService.createNotification(admin, user, message, title, url));
            return mapper.toTeacherResponse(user);
        }
        throw new AppException(ErrorCode.REGISTER_TEACHER_INVALID);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN') and isAuthenticated()")
    public UserRegisterTeacherResponse rejectTeacher(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        String roleName = user.getRole().getName();
        Role role = roleRepository.findByName(PredefinedRole.TEACHER_ROLE)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

        if (user.getRegistrationStatus().equals(RegistrationStatus.PENDING) && roleName.equals(PredefinedRole.USER_ROLE)) {
            user.setRegistrationStatus(RegistrationStatus.REJECTED);
            user.setRole(role);
            user.setBio(null);
            user.setCertificate(null);
            user.setCvUrl(null);
            user.setFacebookLink(null);
            userRepository.save(user);
            log.info("RegistrationStatus {}", user.getRegistrationStatus());

            String message = "Your application to become a teacher has been rejected.";
            String title = "Teacher Registration Rejected";
            String url = "/support";
            List<User> userAdmin = userRepository.findByRoleName(PredefinedRole.ADMIN_ROLE);
            for (User usersAdmin : userAdmin) {
                notificationService.createNotification(user, usersAdmin, message, title, url);
            }
        }
        return mapper.toTeacherResponse(user);
    }


}
