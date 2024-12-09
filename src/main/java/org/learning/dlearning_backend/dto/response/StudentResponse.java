package org.learning.dlearning_backend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentResponse {
    String name;
    String email;
    String avatar;
    String courseName;

    @JsonFormat(pattern = "EEEE, dd MMMM yyyy")
    LocalDateTime createAt;
}
