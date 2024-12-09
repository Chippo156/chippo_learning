package org.learning.dlearning_backend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfoTeacherByCourseResponse {

    Long userId;
    String name;
    String avatar;
    BigDecimal avgRating;
    Integer reviewAmount;
    Integer studentAmount;
    Integer courseAmount;
    String description;
}
