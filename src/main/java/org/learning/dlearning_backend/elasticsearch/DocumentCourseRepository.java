package org.learning.dlearning_backend.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentCourseRepository extends ElasticsearchRepository<CourseDocument, String> {

    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"title^3\"], \"fuzziness\": \"AUTO\", \"minimum_should_match\": \"30%\"}}")
    List<CourseDocument> findByTitle(String keyword);

}
