package org.learning.dlearning_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "enrollments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Enrollment extends AbstractEntity<Long>{
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("enrollments")
    User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnoreProperties("enrollments")
    Course course;

    @Column(name = "is_purchased")
    boolean purchased;

    @Column(name = "is_complete")
    boolean isComplete;

    @PrePersist
    public void prePersist () {
        this.isComplete = false;
        this.purchased = true;
    }

}
