package org.learning.dlearning_backend.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.dto.request.CreationChapterRequest;
import org.learning.dlearning_backend.dto.response.CreationChapterResponse;
import org.learning.dlearning_backend.entity.Chapter;
import org.learning.dlearning_backend.entity.Course;
import org.learning.dlearning_backend.entity.User;
import org.learning.dlearning_backend.exception.AppException;
import org.learning.dlearning_backend.exception.ErrorCode;
import org.learning.dlearning_backend.mapper.ChapterMapper;
import org.learning.dlearning_backend.repository.ChapterRepository;
import org.learning.dlearning_backend.repository.CourseRepository;
import org.learning.dlearning_backend.repository.UserRepository;
import org.learning.dlearning_backend.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChapterService {
    ChapterRepository repository;
    ChapterMapper chapterMapper;
    UserRepository userRepository;
    CourseRepository courseRepository;

    @Transactional
    @PreAuthorize("isAuthenticated() and hasAnyAuthority('ADMIN', 'TEACHER')")
    public CreationChapterResponse createChapter(CreationChapterRequest request){
        String email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_INVALID));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_EXISTED));

        Chapter chapter = chapterMapper.toChapter(request);
        chapter.setCourse(course);
        repository.save(chapter);

        return CreationChapterResponse.builder()
                .userName(user.getName())
                .courseId(course.getId())
                .chapterId(chapter.getId())
                .chapterName(chapter.getChapterName())
                .description(chapter.getDescription())
                .lessons(chapter.getLessons().stream()
                        .map(lesson ->
                                CreationChapterResponse.
                                        LessonDto.builder()
                                        .lessonId(lesson.getId())
                                        .lessonName(lesson.getLessonName())
                                        .lessonDescription(lesson.getDescription())
                                        .videoUrl(lesson.getVideoUrl())
                                        .build())
                        .collect(Collectors.toSet())
                )
                .build();
    }

}
