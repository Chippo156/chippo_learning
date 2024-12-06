package org.learning.dlearning_backend.repository;

import org.learning.dlearning_backend.common.PaymentStatus;
import org.learning.dlearning_backend.entity.Payment;
import org.learning.dlearning_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
    @Query("SELECT p FROM Payment p WHERE p.user = :user AND p.createdAt BETWEEN :start AND :end ORDER BY p.createdAt DESC")
    List<Payment> findByUserAndDateRange(@Param("user") User user, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


    @Query("SELECT p FROM Payment p WHERE p.course.author = :author AND p.status = :status AND p.createdAt BETWEEN :startDate AND :endDate")
    List<Payment> findPaymentsByAuthorStatusAndDateRange(@Param("author") User author, @Param("status") PaymentStatus status,
                                                         @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
