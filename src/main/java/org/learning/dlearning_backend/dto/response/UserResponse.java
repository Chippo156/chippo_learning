package org.learning.dlearning_backend.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.learning.dlearning_backend.entity.Role;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String email;
    String firstName;
    String lastName;
    LocalDate dob;
    Boolean noPassword;
    Set<Role> roles = new HashSet<>();
}
