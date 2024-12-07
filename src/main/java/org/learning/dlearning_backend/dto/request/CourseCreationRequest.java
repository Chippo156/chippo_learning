package org.learning.dlearning_backend.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.learning.dlearning_backend.common.CourseLevel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseCreationRequest {
    String title;
    String description;
    Integer duration;
    String language;
    CourseLevel courseLevel;
    Long points;
}
