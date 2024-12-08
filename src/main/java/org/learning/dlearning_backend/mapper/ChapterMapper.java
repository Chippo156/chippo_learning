package org.learning.dlearning_backend.mapper;

import org.learning.dlearning_backend.dto.request.CreationChapterRequest;
import org.learning.dlearning_backend.dto.response.CreationChapterResponse;
import org.learning.dlearning_backend.entity.Chapter;
import org.learning.dlearning_backend.entity.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChapterMapper {

    @Mapping(source = "courseId", target = "course.id")
    @Mapping(source = "chapterName", target = "chapterName")
    @Mapping(source = "description", target = "description")
    Chapter toChapter(CreationChapterRequest request);

    @Mapping(target = "lessonId", source = "id")
    @Mapping(target = "lessonName", source = "lessonName")
    @Mapping(target = "videoUrl", source = "videoUrl")
    @Mapping(target = "lessonDescription", source = "description")
    CreationChapterResponse.LessonDto toLessonDto(Lesson lesson);
}
