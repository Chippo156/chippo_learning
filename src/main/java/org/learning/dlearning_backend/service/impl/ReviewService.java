package org.learning.dlearning_backend.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.dto.request.ReviewRequest;
import org.learning.dlearning_backend.dto.request.UpdateReviewRequest;
import org.learning.dlearning_backend.dto.response.DeleteCommentResponse;
import org.learning.dlearning_backend.dto.response.ReviewResponse;
import org.learning.dlearning_backend.dto.response.UpdateReviewResponse;
import org.learning.dlearning_backend.entity.Course;
import org.learning.dlearning_backend.entity.Review;
import org.learning.dlearning_backend.entity.User;
import org.learning.dlearning_backend.exception.AppException;
import org.learning.dlearning_backend.exception.ErrorCode;
import org.learning.dlearning_backend.mapper.ReviewMapper;
import org.learning.dlearning_backend.repository.CourseRepository;
import org.learning.dlearning_backend.repository.ReviewRepository;
import org.learning.dlearning_backend.repository.UserRepository;
import org.learning.dlearning_backend.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ReviewService {

    UserRepository userRepository;
    ReviewRepository reviewRepository;
    ReviewMapper reviewMapper;
    CourseRepository courseRepository;
    BannedWordsService bannedWordsService;

    public List<ReviewResponse> getReviewByCourse(Long id) {
        List<Review> allReviews = reviewRepository.findByCourseIdAndChapterIsNullAndLessonIsNull(id);
        return allReviews.stream().filter(comment -> comment.getParentReview() == null).map(reviewMapper::toReviewResponse).toList();
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    public ReviewResponse addReview(ReviewRequest request, Long courseId) {
        String email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_INVALID));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_EXISTED));

        Review parentReview = null;
        if (request.getParentReviewId() != null) {
            parentReview = reviewRepository.findById(request.getParentReviewId())
                    .orElseThrow(() -> new AppException(ErrorCode.PARENT_COMMENT_NOT_EXISTED));
        }
        if (request.getContent() == null || request.getContent().isEmpty() && request.getRating() == null) {
            throw new AppException(ErrorCode.INVALID_COMMENT_OR_RATING);

        }
        if (request.getRating() != null && (request.getRating() < 0 || request.getRating() > 5)) {
            throw new AppException(ErrorCode.INVALID_RATING);

        }
        if (request.getContent() != null && bannedWordsService.containsBannedWords(request.getContent())) {
            throw new AppException(ErrorCode.INVALID_COMMENT_CONTENT);
        }

        Review newComment = Review.builder()
                .user(user)
                .content(request.getContent() != null && !request.getContent().isEmpty() ? request.getContent() : "")
                .rating(request.getRating())
                .course(course)
                .parentReview(parentReview)
                .build();
        reviewRepository.save(newComment);
        return reviewMapper.toReviewResponse(newComment);
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    public DeleteCommentResponse deleteCommentById(Long id) {
        String email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_INVALID));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Review comment = reviewRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_EXISTED));

        if (Objects.equals(user.getId(), comment.getUser().getId())) {
            reviewRepository.deleteById(id);
            return DeleteCommentResponse.builder()
                    .id(id)
                    .message("Delete Comment Successfully")
                    .build();
        }
        throw new AppException(ErrorCode.DELETE_COMMENT_INVALID);
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    public UpdateReviewResponse updateComment(UpdateReviewRequest request, Long id) {
        String email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_INVALID));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Review comment = reviewRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_EXISTED));

        if (Objects.equals(user.getId(), comment.getUser().getId())) {
            if (request.getContent() != null && bannedWordsService.containsBannedWords(request.getContent())) {
                throw new AppException(ErrorCode.INVALID_COMMENT_CONTENT);
            }
            if (request.getContent() != null) {
                comment.setContent(request.getContent());
            }
            reviewRepository.save(comment);

            return UpdateReviewResponse.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .build();
        }
        throw new AppException(ErrorCode.UPDATE_COMMENT_INVALID);
    }

}
