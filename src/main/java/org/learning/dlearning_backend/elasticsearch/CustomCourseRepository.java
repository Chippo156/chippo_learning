package org.learning.dlearning_backend.elasticsearch;

import java.util.List;

public interface CustomCourseRepository {
    List<CourseDocument> findByDynamicTitleSearch(String title);
}
