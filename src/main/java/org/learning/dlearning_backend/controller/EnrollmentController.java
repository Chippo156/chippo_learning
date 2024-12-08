package org.learning.dlearning_backend.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.dto.request.IsCourseCompleteRequest;
import org.learning.dlearning_backend.dto.response.ApiResponse;
import org.learning.dlearning_backend.dto.response.BuyCourseResponse;
import org.learning.dlearning_backend.dto.response.CoursePurchaseResponse;
import org.learning.dlearning_backend.dto.response.IsCourseCompleteResponse;
import org.learning.dlearning_backend.service.impl.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1")
@Slf4j
public class EnrollmentController {
    EnrollmentService enrollmentService;

    @GetMapping("/users/me/courses")
    ApiResponse<List<BuyCourseResponse>> getCourseByCurrentUser() {
        return ApiResponse.<List<BuyCourseResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("My Courses")
                .result(enrollmentService.getCourseByUserCurrent())
                .build();
    }
    @GetMapping("/check-purchase/{courseId}")
    ApiResponse<CoursePurchaseResponse> checkPurchase(@PathVariable Long courseId) {
        var result = enrollmentService.checkPurchase(courseId);
        return ApiResponse.<CoursePurchaseResponse>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .build();
    }
    @PostMapping("/course/is-complete")
    ApiResponse<IsCourseCompleteResponse> isComplete(@RequestBody IsCourseCompleteRequest request) {
        return ApiResponse.<IsCourseCompleteResponse>builder()
                .code(HttpStatus.OK.value())
                .result(enrollmentService.isCompleteCourse(request))
                .build();
    }

}
