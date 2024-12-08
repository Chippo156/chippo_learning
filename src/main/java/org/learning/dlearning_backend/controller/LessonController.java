package org.learning.dlearning_backend.controller;

import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.dto.request.CommentLessonRequest;
import org.learning.dlearning_backend.dto.request.LessonCreationRequest;
import org.learning.dlearning_backend.dto.request.UpdateLessonRequest;
import org.learning.dlearning_backend.dto.response.ApiResponse;
import org.learning.dlearning_backend.dto.response.CommentLessonResponse;
import org.learning.dlearning_backend.dto.response.LessonCreationResponse;
import org.learning.dlearning_backend.dto.response.UpdateLessonResponse;
import org.learning.dlearning_backend.service.impl.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1")
@Slf4j
public class LessonController {

    LessonService lessonService;

    @PostMapping("/create-lesson")
    ApiResponse<LessonCreationResponse> createLesson(@RequestPart("request") LessonCreationRequest request,
                                                     @RequestPart("video") MultipartFile file)
            throws IOException {
        var result = lessonService.createLesson(request, file);
        return ApiResponse.<LessonCreationResponse>builder()
                .code(HttpStatus.CREATED.value())
                .result(result)
                .build();
    }

    @PutMapping("/update-lesson")
    ApiResponse<UpdateLessonResponse> updateLesson(
            @RequestPart("request") UpdateLessonRequest request,
            @RequestPart(value = "video", required = false) MultipartFile video)
            throws IOException {

        var result = lessonService.updateLesson(request, video);
        return ApiResponse.<UpdateLessonResponse>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .build();
    }
    @DeleteMapping("/delete-lesson/{lessonId}")
    ApiResponse<Void> deleteLesson (@PathVariable Long lessonId ) {
        lessonService.deleteLesson(lessonId);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .build();
    }
    @GetMapping("/get-comment-lesson/{lessonId}")
    ApiResponse<List<CommentLessonResponse>> getCommentLesson(@PathVariable @Min(1) Long lessonId){
        var result = lessonService.getCommentByLesson(lessonId);
        return ApiResponse.<List<CommentLessonResponse>>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .build();
    }
    @PostMapping("/add-comment-lesson")
    ApiResponse<CommentLessonResponse> addCommentLesson(@RequestBody CommentLessonRequest request){
        var result = lessonService.addCommentLesson(request);
        return ApiResponse.<CommentLessonResponse>builder()
                .code(HttpStatus.CREATED.value())
                .result(result)
                .build();
    }

    @DeleteMapping("/delete-comment-lesson/{commentId}")
    ApiResponse<Void> deleteCommentLesson(@PathVariable Long commentId){
        lessonService.deleteCommentLesson(commentId);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .build();
    }


}
