package org.learning.dlearning_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.learning.dlearning_backend.common.Gender;
import org.learning.dlearning_backend.common.RegistrationStatus;
import org.learning.dlearning_backend.validation.DateOfBirth;
import org.learning.dlearning_backend.validation.PhoneNumber;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends AbstractEntity<Long> {
    @Column(name = "email", nullable = false, unique = true)
    @NotBlank
    @Email
    String email;
    @Column(name = "password")
    String password;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name", nullable = false)
    String lastName;

    @Column(name = "avatar")
    String avatar;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    Gender gender;

    @Column(name = "phone")
    @PhoneNumber
    String phone;

    @Column(name = "dob")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @DateOfBirth
    LocalDate dob;

    @Column(name = "otp")
    String otp;

    @Column(name = "otp_expiry_date")
    LocalDateTime otpExpiryDate;

    @Column(name = "address")
    String address;

    @Column(name = "description", columnDefinition = "MEDIUMTEXT")
    String description;

    @Column(name = "zipCode")
    String zipCode;

    @Column(name = "enabled")
    Boolean enabled;

    @Column(name = "expertise")
    String expertise;

    @Column(name = "yearsOfExperience")
    Double yearsOfExperience;

    @Column(name = "bio")
    String bio;

    @Column(name = "certificate")
    String certificate;

    @Column(name = "cvUrl")
    String cvUrl;

    @Column(name = "facebookLink")
    String facebookLink;

    @Column(name = "points", columnDefinition = "BIGINT DEFAULT 0")
    Long points;

    @Column(name = "registrationStatus")
    @Enumerated(EnumType.STRING)
    RegistrationStatus registrationStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    Role role;
}
