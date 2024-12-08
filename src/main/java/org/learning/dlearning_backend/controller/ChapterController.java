package org.learning.dlearning_backend.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.dto.request.CreationChapterRequest;
import org.learning.dlearning_backend.dto.response.ApiResponse;
import org.learning.dlearning_backend.dto.response.CreationChapterResponse;
import org.learning.dlearning_backend.service.impl.ChapterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1")
@Slf4j
public class ChapterController {
    ChapterService chapterService;
    @PostMapping("/create/chapter")
    @ResponseStatus(HttpStatus.CREATED)
    ApiResponse<CreationChapterResponse> createChapter(@RequestBody @Valid CreationChapterRequest request) {
        return ApiResponse.<CreationChapterResponse>builder()
                .code(HttpStatus.CREATED.value())
                .result(chapterService.createChapter(request))
                .build();
    }



}
