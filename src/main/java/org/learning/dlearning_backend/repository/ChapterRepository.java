package org.learning.dlearning_backend.repository;

import org.learning.dlearning_backend.entity.Chapter;
import org.learning.dlearning_backend.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    Optional<Chapter> findByChapterNameAndCourse(String chapterName, Course course);

}
