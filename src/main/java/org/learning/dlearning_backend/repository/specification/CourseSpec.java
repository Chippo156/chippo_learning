package org.learning.dlearning_backend.repository.specification;

import org.learning.dlearning_backend.entity.Course;
import org.springframework.data.jpa.domain.Specification;

public class CourseSpec {
    private CourseSpec() {}
    public static Specification<Course> hasFirstName (String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }
    public static Specification<Course> hasGender (Long point) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("price"), point);
    }
}
