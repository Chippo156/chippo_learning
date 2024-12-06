package org.learning.dlearning_backend.repository;

import org.learning.dlearning_backend.entity.Course;
import org.learning.dlearning_backend.entity.Lesson;
import org.learning.dlearning_backend.entity.LessonProgress;
import org.learning.dlearning_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {
    @Query("SELECT COUNT(lp) FROM LessonProgress lp WHERE lp.user = :user AND lp.lesson.chapter.course = :course AND lp.completed = :completed")
    long countByUserAndCourseAndCompleted(@Param("user") User user, @Param("course") Course course, @Param("completed") Boolean completed);

    @Query("SELECT lp FROM LessonProgress lp WHERE lp.user = :user AND lp.lesson.chapter.course = :course AND lp.completed = :completed")
    List<LessonProgress> findByUserAndCourse(@Param("user") User user, @Param("course") Course course, @Param("completed") Boolean completed);

    LessonProgress findByUserAndLesson(User user, Lesson lesson);

    @Query("select count(lp) from LessonProgress lp where lp.user=:user and lp.lesson.chapter.course=:course")
    long totalLessonComplete(User user, Course course);

}
