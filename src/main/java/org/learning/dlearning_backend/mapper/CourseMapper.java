package org.learning.dlearning_backend.mapper;


import org.learning.dlearning_backend.dto.request.CourseCreationRequest;
import org.learning.dlearning_backend.dto.request.UploadCourseRequest;
import org.learning.dlearning_backend.dto.response.CourseCreationResponse;
import org.learning.dlearning_backend.dto.response.CourseResponse;
import org.learning.dlearning_backend.dto.response.UploadCourseResponse;
import org.learning.dlearning_backend.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(source = "author.name", target = "author")
    @Mapping(target = "averageRating", ignore = true)
    CourseResponse toCourseResponse(Course course);

    Course toCourse(CourseCreationRequest courseRequest);

    @Mapping(source = "author.name", target = "author")
    CourseCreationResponse toCourseCreationResponse(Course course);

    Course updateCourse(UploadCourseRequest request);

    @Mapping(source = "author.name", target = "author")
    @Mapping(source = "thumbnail", target = "thumbnail")
    UploadCourseResponse toUploadCourseResponse(Course course);

}
