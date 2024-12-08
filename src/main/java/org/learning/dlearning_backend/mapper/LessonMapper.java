package org.learning.dlearning_backend.mapper;

import org.learning.dlearning_backend.dto.request.UpdateLessonRequest;
import org.learning.dlearning_backend.dto.response.CommentLessonResponse;
import org.learning.dlearning_backend.entity.Lesson;
import org.learning.dlearning_backend.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LessonMapper
{
    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "chapterId", source = "chapter.id")
    @Mapping(target = "lessonId", source = "lesson.id")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "avatar", source = "user.avatar")
    @Mapping(target = "reviewId", source = "id")
    CommentLessonResponse toCommentLessonResponse(Review review);
    void updateLesson(UpdateLessonRequest request, @MappingTarget Lesson lesson);
}
