package org.learning.dlearning_backend.elasticsearch.impl;


import org.learning.dlearning_backend.elasticsearch.CourseDocument;
import org.learning.dlearning_backend.elasticsearch.CustomCourseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomCourseRepositoryImpl implements CustomCourseRepository {

    @Override
    public List<CourseDocument> findByDynamicTitleSearch(String title) {
        return List.of();
    }
}
