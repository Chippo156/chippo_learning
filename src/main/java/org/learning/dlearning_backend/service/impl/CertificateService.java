package org.learning.dlearning_backend.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.dto.event.CertificateCreationEvent;
import org.learning.dlearning_backend.dto.response.CertificateResponse;
import org.learning.dlearning_backend.entity.Course;
import org.learning.dlearning_backend.entity.Enrollment;
import org.learning.dlearning_backend.entity.User;
import org.learning.dlearning_backend.exception.AppException;
import org.learning.dlearning_backend.exception.ErrorCode;
import org.learning.dlearning_backend.repository.*;
import org.learning.dlearning_backend.utils.SecurityUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CertificateService {
    CertificateRepository certificateRepository;
    UserRepository userRepository;
    CourseRepository courseRepository;
    LessonProgressRepository lessonProgressRepository;
    KafkaTemplate<String, Object> kafkaTemplate;
    EnrollmentRepository enrollmentRepository;

    public void createCertificate(CertificateCreationEvent event) {
        Course course = courseRepository.findById(event.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_EXISTED));
        String email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        long totalLesson = course.getChapters().stream()
                .mapToLong(chapter -> chapter.getLessons().size()).sum();
        if (!enrollmentRepository.existsByUserAndCourse(user, course)) {
            throw new AppException(ErrorCode.COURSE_NOT_PURCHASED);
        }
        if (lessonProgressRepository.totalLessonComplete(user, course) < totalLesson) {
            throw new AppException(ErrorCode.INCOMPLETE_LESSONS);
        }
        if (certificateRepository.existsByCourseAndUser(course, user)) {
            throw new AppException(ErrorCode.CERTIFICATE_EXISTED);
        }
        Enrollment enrollment = enrollmentRepository.findByCourseAndUser(course, user)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_PURCHASED));

        enrollment.setComplete(true);
        enrollmentRepository.save(enrollment);

        CertificateCreationEvent creationEvent = new CertificateCreationEvent(user.getId(), event.getCourseId());
        kafkaTemplate.send("certificate-creation", creationEvent);

        log.info("Certificate creation event published for userId: {}, courseId: {}", event.getUserId(), event.getCourseId());


    }
    @PreAuthorize("isAuthenticated()")
    public List<CertificateResponse> getCertificateByUserLogin(){
        String email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return certificateRepository.findByUser(user)
                .stream()
                .map(certificate -> CertificateResponse.builder()
                        .certificateId(certificate.getId())
                        .courseName(certificate.getCourse().getTitle())
                        .author(certificate.getCourse().getAuthor().getName())
                        .email(certificate.getUser().getEmail())
                        .username(certificate.getUser().getName())
                        .certificateUrl(certificate.getCertificateUrl())
                        .issueDate(certificate.getIssueDate())
                        .build())
                .toList();
    }

}
