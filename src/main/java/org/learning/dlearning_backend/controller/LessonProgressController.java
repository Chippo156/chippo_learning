package org.learning.dlearning_backend.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.dto.request.LessonProgressRequest;
import org.learning.dlearning_backend.dto.response.ApiResponse;
import org.learning.dlearning_backend.dto.response.LessonProgressResponse;
import org.learning.dlearning_backend.dto.response.UserCompletionResponse;
import org.learning.dlearning_backend.service.impl.LessonProgressService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1")
@Slf4j
public class LessonProgressController {

    LessonProgressService lessonProgressService;

    @GetMapping("/calculate-completion/{courseId}")
    ApiResponse<UserCompletionResponse> calculateCompletion (@PathVariable Long courseId) {

        return ApiResponse.<UserCompletionResponse>builder()
                .code(HttpStatus.OK.value())
                .result(lessonProgressService.calculateCompletion(courseId))
                .build();
    }
    @PostMapping("/update-lesson-progress")
    ApiResponse<LessonProgressResponse> markLessonAsCompleted(@RequestBody LessonProgressRequest request) {
        return ApiResponse.<LessonProgressResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Update successfully")
                .result(lessonProgressService.markLessonAsCompleted(request))
                .build();
    }


}
