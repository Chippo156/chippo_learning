package org.learning.dlearning_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Course> courses;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "reviews"})
    Set<Review> reviews;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "user"})
    Set<Enrollment> enrollments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Favorite> favorites;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonInclude
    List<Post> posts;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Message> sentMessages;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Message> receivedMessages;

    @PrePersist
    protected void onCreate() {
        if (enabled == null) {
            enabled = Boolean.TRUE;
        }
    }
}
