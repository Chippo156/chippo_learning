package org.learning.dlearning_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.learning.dlearning_backend.common.AdsStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "advertisements")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Advertisement extends AbstractEntity<Long>{

    @Column(name = "title", nullable = false, length = 100)
    String title;

    @Column(name = "description", columnDefinition = "MEDIUMTEXT")
    String description;

    @Email
    @NotNull
    @Column(name = "contact_email", nullable = false)
    String contactEmail;

    @Column(name = "contact_phone", nullable = false)
    String contactPhone;

    @Column(name = "price")
    @Min(value = 0)
    BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    Course course;

    @Column(name = "image_ads")
    String image;

    @Column(name = "localtion")
    String location;

    @Column(name = "link")
    String link;

    @Column(name = "start_date")
    LocalDate startDate;

    @Column(name = "end_date")
    LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status")
    AdsStatus approvalStatus;

}
