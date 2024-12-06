package org.learning.dlearning_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "certificates")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Certificate extends AbstractEntity<Long> {

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "issue_date")
    LocalDateTime issueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    Course course;

    @Column(name = "certificate_url")
    String certificateUrl;

    @Column(name = "description")
    String description;

}
