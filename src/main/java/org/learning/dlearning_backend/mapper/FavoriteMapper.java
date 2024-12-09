package org.learning.dlearning_backend.mapper;

import org.learning.dlearning_backend.dto.response.FavoriteResponse;
import org.learning.dlearning_backend.entity.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {

    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "author", source = "course.author.name")
    @Mapping(target = "title", source = "course.title")
    @Mapping(target = "thumbnail", source = "course.thumbnail")
    @Mapping(target = "points", source = "course.points")
    @Mapping(target = "favoriteId", source = "id")
    FavoriteResponse toFavoriteResponse(Favorite favorite);
}
