package org.learning.dlearning_backend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.constant.PredefinedRole;
import org.learning.dlearning_backend.dto.request.IsCourseCompleteRequest;
import org.learning.dlearning_backend.dto.response.BuyCourseResponse;
import org.learning.dlearning_backend.dto.response.CoursePurchaseResponse;
import org.learning.dlearning_backend.dto.response.IsCourseCompleteResponse;
import org.learning.dlearning_backend.entity.Course;
import org.learning.dlearning_backend.entity.Enrollment;
import org.learning.dlearning_backend.entity.User;
import org.learning.dlearning_backend.exception.AppException;
import org.learning.dlearning_backend.exception.ErrorCode;
import org.learning.dlearning_backend.mapper.EnrollmentMapper;
import org.learning.dlearning_backend.repository.CourseRepository;
import org.learning.dlearning_backend.repository.EnrollmentRepository;
import org.learning.dlearning_backend.repository.UserRepository;
import org.learning.dlearning_backend.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Slf4j
public class EnrollmentService {

    EnrollmentRepository enrollmentRepository;
    UserRepository userRepository;
    CourseRepository courseRepository;
    EnrollmentMapper enrollmentMapper;

    @PreAuthorize("isAuthenticated()")
    public List<BuyCourseResponse> getCourseByUserCurrent() {
        String email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_INVALID));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        List<Enrollment> enrollments = enrollmentRepository.findCourseByUser(user);
        return enrollments.stream().map(enrollmentMapper::toBuyCourseResponse).toList();
    }

    @PreAuthorize("isAuthenticated()")
    public CoursePurchaseResponse checkPurchase(Long courseId) {
        if (courseId <= 0) {
            throw new AppException(ErrorCode.INVALID_PATH_VARIABLE_ID);
        }
        String email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_INVALID));
        User userCurrent = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_EXISTED));
        if (Objects.equals(userCurrent.getRole().getName(), PredefinedRole.ADMIN_ROLE)) {
            return CoursePurchaseResponse.builder()
                    .userId(userCurrent.getId())
                    .courseId(course.getId())
                    .purchased(true)
                    .build();
        }
        Optional<Enrollment> enrollment = enrollmentRepository.checkPurchase(userCurrent, course);
        if (enrollment.isEmpty()) {
            return CoursePurchaseResponse.builder()
                    .userId(userCurrent.getId())
                    .courseId(courseId)
                    .purchased(false)
                    .build();
        }
        return enrollmentMapper.toCoursePurchaseResponse(enrollment.get());
    }
    public IsCourseCompleteResponse isCompleteCourse (IsCourseCompleteRequest request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_EXISTED));

        String email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_INVALID));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return IsCourseCompleteResponse.builder()
                .isComplete(enrollmentRepository.isCourseCompleteByUser(user, course))
                .build();
    }
}
