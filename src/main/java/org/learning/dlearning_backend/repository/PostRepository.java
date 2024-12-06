package org.learning.dlearning_backend.repository;

import org.learning.dlearning_backend.entity.Post;
import org.learning.dlearning_backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    Page<Post> findPostByUser(User user, Pageable pageable, Specification<Post> specification);

}
