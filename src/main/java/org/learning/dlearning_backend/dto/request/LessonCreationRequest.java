package org.learning.dlearning_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonCreationRequest {

    @NotNull(message = "COURSE_ID_INVALID")
    Long courseId;

    @NotNull(message = "CHAPTER_ID_INVALID")
    Long chapterId;

    @NotBlank(message = "LESSON_NAME_INVALID")
    String lessonName;

    String description;
}
