package org.learning.dlearning_backend.repository;

import org.learning.dlearning_backend.entity.Certificate;
import org.learning.dlearning_backend.entity.Course;
import org.learning.dlearning_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CertificateRepository  extends JpaRepository<Certificate, Long> {
    boolean existsByCourseAndUser(Course course, User user);
    List<Certificate> findByUser(User user);

    @Query("select c from Certificate c where c.course.id=:id")
    List<Certificate> getAllCertificateById(Long id);
}
