package org.learning.dlearning_backend.mapper;

import org.learning.dlearning_backend.dto.response.BuyCourseResponse;
import org.learning.dlearning_backend.dto.response.CoursePurchaseResponse;
import org.learning.dlearning_backend.entity.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    @Mapping(target = "courseLevel", source = "course.courseLevel")
    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "title", source = "course.title")
    @Mapping(target = "points", source = "course.points")
    @Mapping(target = "author", source = "course.author.name")
    @Mapping(target = "thumbnail", source = "course.thumbnail")
    @Mapping(target = "createAt", source = "course.createdAt")
    BuyCourseResponse toBuyCourseResponse(Enrollment enrollment);

    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "purchased", source = "purchased")
    CoursePurchaseResponse toCoursePurchaseResponse(Enrollment enrollment);

}
