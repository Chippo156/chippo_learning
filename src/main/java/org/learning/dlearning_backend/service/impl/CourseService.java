package org.learning.dlearning_backend.service.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.constant.PredefinedRole;
import org.learning.dlearning_backend.dto.request.CourseCreationRequest;
import org.learning.dlearning_backend.dto.response.CourseCreationResponse;
import org.learning.dlearning_backend.dto.response.CourseResponse;
import org.learning.dlearning_backend.dto.response.PageResponse;
import org.learning.dlearning_backend.elasticsearch.CourseDocument;
//import org.learning.dlearning_backend.elasticsearch.DocumentCourseRepository;
import org.learning.dlearning_backend.elasticsearch.DocumentCourseRepository;
import org.learning.dlearning_backend.entity.Course;
import org.learning.dlearning_backend.entity.Review;
import org.learning.dlearning_backend.entity.User;
import org.learning.dlearning_backend.exception.AppException;
import org.learning.dlearning_backend.exception.ErrorCode;
import org.learning.dlearning_backend.mapper.CourseMapper;
import org.learning.dlearning_backend.repository.CourseRepository;
import org.learning.dlearning_backend.repository.UserRepository;
import org.learning.dlearning_backend.repository.specification.CourseSpecificationBuilder;
import org.learning.dlearning_backend.repository.specification.SpecSearchCriteria;
import org.learning.dlearning_backend.utils.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CourseService {
    UserRepository userRepository;
    CourseRepository courseRepository;
    CourseMapper courseMapper;
    CloudinaryService cloudinaryService;

        DocumentCourseRepository documentCourseRepository;
    public PageResponse<CourseResponse> getAllCourses(Specification<Course> spec, int page, int size) {
        log.warn("getAllCourses: spec: {}, page: {}, size: {}", spec, page, size);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Course> pageData = courseRepository.findAll(spec, pageable);

        List<CourseResponse> courseResponses = pageData.getContent()
                .stream().map(course -> {

                    List<Review> filteredComments = course.getComments().stream()
                            .filter(r -> r.getRating() > 0)
                            .toList();

                    long totalRating = filteredComments.stream()
                            .mapToLong(Review::getRating)
                            .sum();

                    int numberOfValidReviews = filteredComments.size();
                    double averageRating = numberOfValidReviews > 0 ? BigDecimal.valueOf((double) totalRating / numberOfValidReviews)
                            .setScale(2, RoundingMode.HALF_UP)
                            .doubleValue() : 0.0;

                    CourseResponse courseResponse = courseMapper.toCourseResponse(course);
                    courseResponse.setAverageRating(averageRating);
                    return courseResponse;
                }).sorted(Comparator.comparing(CourseResponse::getAverageRating)
                        .reversed())
                .toList();

        return PageResponse.<CourseResponse>builder()
                .currentPage(page)
                .pageSize(pageable.getPageSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(courseResponses)
                .build();
    }

    @Transactional
    @PreAuthorize("isAuthenticated() and hasAnyAuthority('TEACHER', 'ADMIN')")
    public CourseCreationResponse createCourse(CourseCreationRequest request,
                                               MultipartFile thumbnail,
                                               MultipartFile video)
            throws IOException {
        String email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_INVALID));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

//        if(!Objects.equals(user.getRole().getName(), PredefinedRole.TEACHER_ROLE)){
//            throw new AppException(ErrorCode.ACCESS_DENIED);
//        }

        Course course = courseMapper.toCourse(request);
        String urlThumbnail = cloudinaryService.uploadImage(thumbnail);
        if (video != null) {
            String videoUrl = cloudinaryService.uploadVideoChunked(video, "courses").get("url").toString();
            course.setVideoUrl(videoUrl);
        }
        course.setThumbnail(urlThumbnail);
        course.setAuthor(user);
        courseRepository.save(course);
        saveCourseDocument(course);

        return courseMapper.toCourseCreationResponse(course);
    }

        public void saveCourseDocument(Course course) {

        CourseDocument courseDocument = CourseDocument.builder()
                .id(String.valueOf(course.getId()))
                .author(course.getAuthor().getName())
                .title(course.getTitle())
                .description(course.getDescription())
                .thumbnail(course.getThumbnail())
                .points(course.getPoints())
                .build();

        documentCourseRepository.save(courseDocument);
    }
    public CourseResponse getCourseById(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_EXISTED));

        var filteredComments = course.getComments().stream()
                .filter(r -> r.getRating() > 0)
                .toList();

        long totalRating = filteredComments.stream()
                .mapToLong(Review::getRating)
                .sum();

        int numberOfValidReviews = filteredComments.size();
        double averageRating = numberOfValidReviews > 0 ? BigDecimal.valueOf((double) totalRating / numberOfValidReviews)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue() : 0.0;

        return CourseResponse.builder()
                .id(course.getId())
                .author(course.getAuthor().getName())
                .title(course.getTitle())
                .description(course.getDescription())
                .duration(course.getDuration())
                .language(course.getLanguage())
                .courseLevel(course.getCourseLevel())
                .thumbnail(course.getThumbnail())
                .videoUrl(course.getVideoUrl())
                .points(course.getPoints())
                .averageRating(averageRating)
                .build();
    }
    public List<CourseDocument> findByTitle(String title) {
        return documentCourseRepository.findByTitle(title);
    }

}
