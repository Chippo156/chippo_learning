package org.learning.dlearning_backend.elasticsearch;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "course-document")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseDocument {

    @Id
    String id;
    String title;
    String description;
    String thumbnail;
    Long points;
    String author;
}

