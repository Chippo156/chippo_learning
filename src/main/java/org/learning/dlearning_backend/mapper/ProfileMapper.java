package org.learning.dlearning_backend.mapper;

import org.learning.dlearning_backend.dto.request.UserProfileRequest;
import org.learning.dlearning_backend.dto.response.UserProfileResponse;
import org.learning.dlearning_backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    void updateUser(UserProfileRequest request, @MappingTarget User user);
    UserProfileResponse getInfoUser(User user);
}
