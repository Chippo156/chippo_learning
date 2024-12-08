package org.learning.dlearning_backend.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.dto.request.PostCreationRequest;
import org.learning.dlearning_backend.dto.request.UpdateLikeCountRequest;
import org.learning.dlearning_backend.dto.response.ApiResponse;
import org.learning.dlearning_backend.dto.response.PageResponse;
import org.learning.dlearning_backend.dto.response.PostCreationResponse;
import org.learning.dlearning_backend.dto.response.PostResponse;
import org.learning.dlearning_backend.entity.Post;
import org.learning.dlearning_backend.service.impl.PostService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1")
@Slf4j
public class PostController {
    PostService postService;

    @PostMapping("/create-post")
    ApiResponse<PostCreationResponse> createPost (@RequestPart("request") @Valid PostCreationRequest request,
                                                  @RequestPart(value = "file", required = false) MultipartFile file) {
        return ApiResponse.<PostCreationResponse>builder()
                .code(HttpStatus.CREATED.value())
                .result(postService.createPost(request, file))
                .build();
    }
    @DeleteMapping("/delete-post/{postId}")
    ApiResponse<Void> deletePost (@PathVariable Long postId) {
        postService.deletePost(postId);

        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Delete post successfully")
                .build();
    }

    @GetMapping("/get-all-post")
    ApiResponse<PageResponse<PostResponse>> getAllPost (
            @Filter Specification<Post> spec,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "3") int size){

        return ApiResponse.<PageResponse<PostResponse>>builder()
                .code(HttpStatus.OK.value())
                .result(postService.getAllPost(spec, page, size))
                .build();
    }
    @GetMapping("/get-post-current-login")
    ApiResponse<PageResponse<PostResponse>> getPostByCurrentLogin(
            @Filter Specification<Post> spec,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "3") int size
    ){
        return ApiResponse.<PageResponse<PostResponse>>builder()
                .code(HttpStatus.OK.value())
                .result(postService.getPostsByCurrentLogin(spec, page, size))
                .build();
    }

    @PutMapping("/update-like-count")
    ApiResponse<Void> updateLikeCount(@RequestBody UpdateLikeCountRequest request) {
        postService.updateLikeCount(request);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Update like successfully")
                .build();
    }

}
