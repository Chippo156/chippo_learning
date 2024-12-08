package org.learning.dlearning_backend.mapper;

import org.learning.dlearning_backend.dto.request.PostCreationRequest;
import org.learning.dlearning_backend.dto.response.PostCreationResponse;
import org.learning.dlearning_backend.dto.response.PostResponse;
import org.learning.dlearning_backend.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post toPost(PostCreationRequest request);

    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "avatar", source = "user.avatar")
    PostCreationResponse toPostCreationResponse(Post post);

    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "avatar", source = "user.avatar")
    @Mapping(target = "likeCount", source = "likeCount")
    PostResponse toPostResponse(Post post);
}
