package org.learning.dlearning_backend.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.constant.PredefinedRole;
import org.learning.dlearning_backend.dto.request.LessonCreationRequest;
import org.learning.dlearning_backend.dto.request.UpdateLessonRequest;
import org.learning.dlearning_backend.dto.response.LessonCreationResponse;
import org.learning.dlearning_backend.dto.response.UpdateLessonResponse;
import org.learning.dlearning_backend.entity.Chapter;
import org.learning.dlearning_backend.entity.Course;
import org.learning.dlearning_backend.entity.Lesson;
import org.learning.dlearning_backend.entity.User;
import org.learning.dlearning_backend.exception.AppException;
import org.learning.dlearning_backend.exception.ErrorCode;
import org.learning.dlearning_backend.mapper.LessonMapper;
import org.learning.dlearning_backend.repository.*;
import org.learning.dlearning_backend.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LessonService {
    LessonRepository lessonRepository;
    ChapterRepository chapterRepository;
    CourseRepository courseRepository;
    CloudinaryService cloudinaryService;
    UserRepository userRepository;
    ReviewRepository reviewRepository;
    //    BannedWordsService bannedWordsService;
    LessonMapper lessonMapper;

    @Transactional
    @PreAuthorize("isAuthenticated() and hasAuthority('TEACHER')")
    public LessonCreationResponse createLesson(LessonCreationRequest request, MultipartFile video) throws IOException {
        var email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_INVALID));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (!Objects.equals(user.getRole().getName(), PredefinedRole.TEACHER_ROLE)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_EXISTED));
        Chapter chapter = chapterRepository.findById(request.getChapterId())
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_EXIST));
        String videoUrl = cloudinaryService.uploadVideoChunked(video, "courses").get("url").toString();

        Lesson lesson = Lesson.builder()
                .chapter(chapter)
                .lessonName(request.getLessonName())
                .videoUrl(videoUrl)
                .description(request.getDescription())
                .build();
        lessonRepository.save(lesson);
        return LessonCreationResponse.builder()
                .chapterId(chapter.getId())
                .courseId(course.getId())
                .lessonId(lesson.getId())
                .lessonName(lesson.getLessonName())
                .lessonDescription(lesson.getDescription())
                .videoUrl(lesson.getVideoUrl())
                .build();
    }

    @PreAuthorize("isAuthenticated() and hasAnyAuthority('TEACHER', 'ADMIN')")
    @Transactional
    public void deleteLesson(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_EXIST));
        Chapter chapter = lesson.getChapter();
        Course course = chapter.getCourse();
        User userCourse = course.getAuthor();
        var email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_INVALID));
        var userLogin = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if(Objects.equals(userCourse.getId(), userLogin.getId())
                && Objects.equals(userCourse.getRole().getName(), PredefinedRole.TEACHER_ROLE)
                && Objects.equals(userLogin.getRole().getName(), PredefinedRole.TEACHER_ROLE)
                || Objects.equals(userLogin.getRole().getName(), PredefinedRole.ADMIN_ROLE)
        ){
            lessonRepository.delete(lesson);
        }  else {
            throw new AppException(ErrorCode.FORBIDDEN);
        }
    }
    @PreAuthorize("isAuthenticated() and hasAnyAuthority('ADMIN', 'TEACHER')")
    public UpdateLessonResponse updateLesson(UpdateLessonRequest request,MultipartFile video) throws IOException {
        String email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_INVALID));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if(request!=null && request.getLessonId()!=null){
            Course course = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_EXISTED));
            boolean isAdmin = Objects.equals(user.getRole().getName(), PredefinedRole.ADMIN_ROLE);
            boolean isAuthor = Objects.equals(course.getAuthor().getId(), user.getId());

            if (!isAdmin && !isAuthor) {
                throw new AppException(ErrorCode.FORBIDDEN);
            }
            Lesson lesson = lessonRepository.findById(request.getLessonId())
                    .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_EXIST));
            lessonMapper.updateLesson(request,lesson);
            if(video != null && !video.isEmpty()){
                String videoUrl = cloudinaryService.uploadVideoChunked(video, "courses").get("url").toString();
                lesson.setVideoUrl(videoUrl);
            }
            lessonRepository.save(lesson);
            return UpdateLessonResponse.builder()
                    .courseId(request.getCourseId())
                    .chapterId(request.getChapterId())
                    .chapterName(lesson.getChapter().getChapterName())
                    .lessonId(lesson.getId())
                    .lessonName(lesson.getLessonName())
                    .description(lesson.getDescription())
                    .videoUrl(lesson.getVideoUrl())
                    .build();
        }
        throw new AppException(ErrorCode.LESSON_NOT_EXIST);
    }
}
