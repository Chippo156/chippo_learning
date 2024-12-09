package org.learning.dlearning_backend.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.dto.request.FavoriteRequest;
import org.learning.dlearning_backend.dto.response.ApiResponse;
import org.learning.dlearning_backend.dto.response.FavoriteResponse;
import org.learning.dlearning_backend.dto.response.PageResponse;
import org.learning.dlearning_backend.entity.Favorite;
import org.learning.dlearning_backend.service.impl.FavouriteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1")
@Slf4j
public class FavouriteController {
    FavouriteService favouriteService;

    @PostMapping("/save-favourite")
    ApiResponse<Void> saveFavourite(@RequestBody FavoriteRequest request) {
        favouriteService.createFavourite(request);

        return ApiResponse.<Void>builder()
                .code(HttpStatus.CREATED.value())
                .message("Save Favourite Successfully")
                .build();
    }

    @GetMapping("/favourite/{id}")
    ApiResponse<Favorite> getFavourite(@PathVariable Integer id) {
        var result = favouriteService.findById(id);

        return ApiResponse.<Favorite>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .build();
    }

    @DeleteMapping("/delete-favourite/{favouriteId}")
    ApiResponse<Void> deleteFavourite(@PathVariable Integer favouriteId) {
        favouriteService.deleteFavourite(favouriteId);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Delete Favourite Successfully")
                .build();
    }

    @GetMapping("/fetch-all-favourites")
    ApiResponse<PageResponse<FavoriteResponse>> fetchAllFavourite(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                                  @RequestParam(value = "size", required = false, defaultValue = "6") int size) {
        var result = favouriteService.findAllByUserCurrent(page, size);

        return ApiResponse.<PageResponse<FavoriteResponse>>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .build();
    }
}
