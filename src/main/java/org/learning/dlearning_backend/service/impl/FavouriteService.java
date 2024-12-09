package org.learning.dlearning_backend.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.dto.request.FavoriteRequest;
import org.learning.dlearning_backend.dto.response.FavoriteResponse;
import org.learning.dlearning_backend.dto.response.PageResponse;
import org.learning.dlearning_backend.entity.Course;
import org.learning.dlearning_backend.entity.Favorite;
import org.learning.dlearning_backend.entity.User;
import org.learning.dlearning_backend.exception.AppException;
import org.learning.dlearning_backend.exception.ErrorCode;
import org.learning.dlearning_backend.mapper.FavoriteMapper;
import org.learning.dlearning_backend.repository.CourseRepository;
import org.learning.dlearning_backend.repository.FavoriteRepository;
import org.learning.dlearning_backend.repository.UserRepository;
import org.learning.dlearning_backend.utils.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FavouriteService {
    FavoriteRepository favoriteRepository;
    UserRepository userRepository;
    CourseRepository courseRepository;
    FavoriteMapper favoriteMapper;

    @PreAuthorize("isAuthenticated()")
    public void createFavourite(FavoriteRequest request){
        String email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_INVALID));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Course course = courseRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_EXISTED));
        boolean isAlreadyFavourite = favoriteRepository.existsByUserAndCourse(user, course);
        if(isAlreadyFavourite){
            throw new AppException(ErrorCode.ALREADY_IN_FAVORITES);
        }
        Favorite favourite = Favorite.builder()
                .user(user)
                .course(course)
                .build();
        favoriteRepository.save(favourite);
    }
    public Favorite findById(Integer id){
        if ( id <= 0 ){
            throw new AppException(ErrorCode.INVALID_PATH_VARIABLE_ID);
        }
        return favoriteRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FAVORITE_NOT_EXISTED));
    }
    @PreAuthorize("isAuthenticated()")
    public PageResponse<FavoriteResponse> findAllByUserCurrent(int page, int size){
        String email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_INVALID));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Pageable pageable = PageRequest.of(page-1, size);

        Page<Favorite> favorites = favoriteRepository.findByUser(user, pageable);
        return PageResponse.<FavoriteResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(favorites.getTotalPages())
                .totalElements(favorites.getTotalElements())
                .data(favorites.getContent().stream().map(favoriteMapper::toFavoriteResponse).toList())
                .build();
    }
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public void deleteFavourite(Integer favouriteId){
        if(favouriteId<=0){
            throw new AppException(ErrorCode.INVALID_PATH_VARIABLE_ID);
        }
        String email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_INVALID));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        List<Favorite> favorites = favoriteRepository.findByUser(user);
        Favorite favoriteToDelete = favorites.stream()
                .filter(f -> Objects.equals(f.getId(), favouriteId))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.FAVORITE_NOT_FOUND));

        favoriteRepository.delete(favoriteToDelete);

    }



}
