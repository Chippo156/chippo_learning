package org.learning.dlearning_backend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentLessonResponse {
    Long reviewId;
    Long courseId;
    Long chapterId;
    Long lessonId;
    String name;
    String avatar;
    String content;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<CommentLessonResponse> replies = new ArrayList<>();
}
