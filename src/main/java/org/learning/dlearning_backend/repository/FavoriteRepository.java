package org.learning.dlearning_backend.repository;

import org.learning.dlearning_backend.entity.Course;
import org.learning.dlearning_backend.entity.Favorite;
import org.learning.dlearning_backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    Page<Favorite> findByUser(User user, Pageable pageable);

    List<Favorite> findByUser(User user);

    boolean existsByUserAndCourse(User user, Course course);
}
