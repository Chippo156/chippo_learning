package org.learning.dlearning_backend.mapper;

import org.learning.dlearning_backend.dto.response.ReviewLessonResponse;
import org.learning.dlearning_backend.dto.response.ReviewResponse;
import org.learning.dlearning_backend.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "user.name" , target = "name")
    @Mapping(source = "user.avatar",target = "avatar")
    @Mapping(source = "replies", target = "replies")
    ReviewResponse toReviewResponse(Review review);


    @Mapping(source = "user.name" , target = "name")
    @Mapping(source = "user.avatar",target = "avatar")
    @Mapping(source = "replies", target = "replies")
    ReviewLessonResponse toLessonResponse(Review review);

}
