package org.learning.dlearning_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chapters")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chapter extends AbstractEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    Course course;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    Set<Lesson> lessons;

    @Column(name = "chapter_name", nullable = false)
    String chapterName;

    @Column(name = "description", columnDefinition = "MEDIUMTEXT")
    String description;

    @PrePersist
    public void prePersist() {
        this.lessons = new HashSet<>();
    }

}
