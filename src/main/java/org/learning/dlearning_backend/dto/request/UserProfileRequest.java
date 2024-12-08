package org.learning.dlearning_backend.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.learning.dlearning_backend.common.CourseLevel;
import org.learning.dlearning_backend.common.Gender;
import org.learning.dlearning_backend.validation.DateOfBirth;
import org.learning.dlearning_backend.validation.PhoneNumber;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileRequest {

    String avatar;
    String firstName;
    String lastName;
    Gender gender;
    @PhoneNumber(message = "PHONE_NUMBER_INVALID")
    String phone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateOfBirth(message = "INVALID_DOB")
    LocalDate dob;
    String address;

    @Size(max = 400)
    String description;
    CourseLevel courseLevel;
}
