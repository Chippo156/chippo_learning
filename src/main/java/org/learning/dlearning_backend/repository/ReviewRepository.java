package org.learning.dlearning_backend.repository;

import org.learning.dlearning_backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.course.id = :courseId AND r.chapter IS NULL AND r.lesson IS NULL")
    List<Review> findByCourseIdAndChapterIsNullAndLessonIsNull(Long courseId);

    List<Review> findByCourseIdAndChapterIdAndLessonId(Long courseId, Long chapterId, Long lessonId);

    @Query("SELECT r FROM Review r WHERE r.lesson.id = :lessonId AND r.parentReview IS NULL")
    List<Review> findByLessonId(Long lessonId);

}
