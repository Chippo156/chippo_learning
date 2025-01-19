package org.learning.dlearning_backend.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.dto.request.BuyCourseRequest;
import org.learning.dlearning_backend.dto.request.CourseCreationRequest;
import org.learning.dlearning_backend.dto.response.*;
import org.learning.dlearning_backend.elasticsearch.CourseDocument;
import org.learning.dlearning_backend.entity.Course;
import org.learning.dlearning_backend.service.impl.CourseService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1")
@Slf4j
public class CourseController {
    CourseService courseService;
    @PostMapping(value = "/create-course", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<CourseCreationResponse> createCourse(
            @RequestPart("courseRequest") CourseCreationRequest courseRequest,
            @RequestPart(value = "thumbnail",required = false) MultipartFile thumbnail,
            @RequestPart(value = "video", required = false) MultipartFile video) throws IOException {

        var result = courseService.createCourse(courseRequest, thumbnail, video);

        return ApiResponse.<CourseCreationResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Create Course Successfully")
                .result(result)
                .build();
    }
    @GetMapping("/course/{id}")
    ApiResponse<CourseResponse> getCourseById(@PathVariable @Min(1) Long id){
        var result = courseService.getCourseById(id);
        return ApiResponse.<CourseResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Get Course Successfully")
                .result(result)
                .build();
    }
    @GetMapping("/courses")
    ApiResponse<PageResponse<CourseResponse>> getAllCourses(
            @Filter Specification<Course> spec,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "6") int size) {

        PageResponse<CourseResponse> result = courseService.getAllCourses(spec, page, size);

        return ApiResponse.<PageResponse<CourseResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Get All Courses Successfully")
                .result(result)
                .build();
    }
    @GetMapping("/search-title")
    ApiResponse<List<CourseDocument>> findByTitle(@RequestParam String title) {
        return ApiResponse.<List<CourseDocument>>builder()
                .code(HttpStatus.OK.value())
                .result(courseService.findByTitle(title))
                .build();
    }
    @PostMapping("/buy-course")
    ApiResponse<BuyCourseResponse> buyCourse(@RequestBody @Valid BuyCourseRequest request) {
        return ApiResponse.<BuyCourseResponse>builder()
                .code(HttpStatus.OK.value())
                .result(courseService.buyCourse(request))
                .build();
    }
    @GetMapping("/info-course/{id}")
    ApiResponse<CourseChapterResponse> infoCourse(@PathVariable @Min(1) Long id){
        return ApiResponse.<CourseChapterResponse>builder()
                .code(HttpStatus.OK.value())
                .result(courseService.getAllInfoCourse(id))
                .build();
    }
}
