package org.learning.dlearning_backend.repository;

import org.learning.dlearning_backend.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.parentComment IS NULL")
    Page<Comment> findCommentByPostIdAndParentCommentIsNull(Long postId, Pageable pageable);
}
