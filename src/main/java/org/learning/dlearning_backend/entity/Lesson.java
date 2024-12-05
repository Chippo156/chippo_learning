package org.learning.dlearning_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "lessons")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lesson extends AbstractEntity<Long> {

    @Column(name = "lesson_name")
    String lessonName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chapter_id", nullable = false)
    Chapter chapter;

    @Column(name = "content_type")
    String contentType; // "video", "document", etc.

    @Column(name = "content_url")
    String videoUrl;

    @Column(name = "description", columnDefinition = "MEDIUMTEXT")
    String description; // Mô tả nội dung nếu cần

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Review> reviews;

}
