package org.learning.dlearning_backend.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.dto.request.LessonProgressRequest;
import org.learning.dlearning_backend.dto.response.LessonProgressResponse;
import org.learning.dlearning_backend.dto.response.UserCompletionResponse;
import org.learning.dlearning_backend.entity.Course;
import org.learning.dlearning_backend.entity.Lesson;
import org.learning.dlearning_backend.entity.LessonProgress;
import org.learning.dlearning_backend.entity.User;
import org.learning.dlearning_backend.exception.AppException;
import org.learning.dlearning_backend.exception.ErrorCode;
import org.learning.dlearning_backend.repository.*;
import org.learning.dlearning_backend.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LessonProgressService {
    LessonProgressRepository lessonProgressRepository;
    UserRepository userRepository;
    CourseRepository courseRepository;
    LessonRepository lessonRepository;
    EnrollmentRepository enrollmentRepository;

    public UserCompletionResponse calculateCompletion(Long courseId) {
        String email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_INVALID));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_EXISTED));

        if (!enrollmentRepository.existsByUserAndCourse(user, course)) {
            throw new AppException(ErrorCode.COURSE_ACCESS_DENIED);
        }
        int totalLessons = course.getChapters().stream()
                .mapToInt(chapter -> chapter.getLessons().size())
                .sum();
        long completedLessons = lessonProgressRepository.countByUserAndCourseAndCompleted(user, course, true);
        if (totalLessons == 0) {
            return UserCompletionResponse.builder()
                    .totalLessonComplete(0L)
                    .totalLessons(0L)
                    .completionPercentage(BigDecimal.ZERO)
                    .build();
        }
        List<LessonProgress> lessonProgresses = lessonProgressRepository.findByUserAndCourse(user, course, true);
        List<UserCompletionResponse.LessonComplete> listCompletedLessons = new ArrayList<>();

        if (!lessonProgresses.isEmpty()) {
            listCompletedLessons = lessonProgresses.stream()
                    .map(lessonPro -> UserCompletionResponse.LessonComplete.builder()
                            .lessonId(lessonPro.getLesson().getId())
                            .lessonName(lessonPro.getLesson().getLessonName())
                            .build()
                    ).toList();
        }

        BigDecimal completed = BigDecimal.valueOf(completedLessons);
        BigDecimal total = BigDecimal.valueOf(totalLessons);
        BigDecimal percentage = completed.divide(total, 3, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(0, RoundingMode.HALF_UP);
        return UserCompletionResponse.builder()
                .totalLessonComplete(completedLessons)
                .totalLessons((long) totalLessons)
                .lessonCompletes(listCompletedLessons)
                .completionPercentage(percentage)
                .build();
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    public LessonProgressResponse markLessonAsCompleted(LessonProgressRequest request) {
        String email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_INVALID));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_EXIST));

        Course course = lesson.getChapter().getCourse();

        if (!enrollmentRepository.existsByUserAndCourse(user, course)) {
            throw new AppException(ErrorCode.COURSE_ACCESS_DENIED);
        }

        LessonProgress existingProgress = lessonProgressRepository.findByUserAndLesson(user, lesson);
        if (existingProgress != null) {
            return LessonProgressResponse.builder()
                    .lessonId(existingProgress.getLesson().getId())
                    .lessonName(existingProgress.getLesson().getLessonName())
                    .isComplete(existingProgress.getCompleted())
                    .build();
        }

        lessonProgressRepository.save(LessonProgress.builder()
                .user(user)
                .lesson(lesson)
                .completed(true)
                .build());

        return LessonProgressResponse.builder()
                .lessonId(lesson.getId())
                .lessonName(lesson.getLessonName())
                .isComplete(true)
                .build();
    }
}
