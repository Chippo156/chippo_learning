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
public class CreationChapterRequest {

    @NotNull(message = "COURSE_ID_INVALID")
    Long courseId;

    @NotBlank(message = "CHAPTER_NAME_INVALID")
    String chapterName;

    String description;
}
