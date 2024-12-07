package org.learning.dlearning_backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.learning.dlearning_backend.common.CourseLevel;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadCourseResponse {

    String author;
    String title;
    String description;
    CourseLevel courseLevel;
    Integer duration;
    BigDecimal price;
    String thumbnail;
    String videoUrl;
}
