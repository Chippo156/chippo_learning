package org.learning.dlearning_backend.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.constant.PredefinedRole;
import org.learning.dlearning_backend.dto.request.CommentRequest;
import org.learning.dlearning_backend.dto.request.UpdateCommentRequest;
import org.learning.dlearning_backend.dto.response.CommentResponse;
import org.learning.dlearning_backend.dto.response.PageResponse;
import org.learning.dlearning_backend.dto.response.UpdateCommentResponse;
import org.learning.dlearning_backend.entity.Comment;
import org.learning.dlearning_backend.entity.Post;
import org.learning.dlearning_backend.entity.User;
import org.learning.dlearning_backend.exception.AppException;
import org.learning.dlearning_backend.exception.ErrorCode;
import org.learning.dlearning_backend.mapper.CommentMapper;
import org.learning.dlearning_backend.repository.CommentRepository;
import org.learning.dlearning_backend.repository.PostRepository;
import org.learning.dlearning_backend.repository.UserRepository;
import org.learning.dlearning_backend.utils.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CommentService {
    CommentRepository commentRepository;
    PostRepository postRepository;
    UserRepository userRepository;
    BannedWordsService bannedWordsService;
    CommentMapper commentMapper;

    @PreAuthorize("isAuthenticated()")
    public PageResponse<CommentResponse> getCommentByPostId(Long postId, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Comment> parentComments = commentRepository.findCommentByPostIdAndParentCommentIsNull(postId, pageable);
        List<CommentResponse> responses = parentComments.getContent()
                .stream().map(comment -> {
                    CommentResponse response = commentMapper.toCommentResponse(comment);
                    List<CommentResponse> replies = comment.getReplies().stream()
                            .map(commentMapper::toCommentResponse)
                            .toList();
                    response.setReplies(replies);
                    return response;
                }).toList();
        return PageResponse.<CommentResponse>builder()
                .currentPage(page)
                .pageSize(pageable.getPageSize())
                .totalElements(parentComments.getTotalElements())
                .totalPages(parentComments.getTotalPages())
                .data(responses)
                .build();

    }

    @PreAuthorize("isAuthenticated()")
    public CommentResponse addComment(CommentRequest request) {
        String email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_INVALID));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new AppException(ErrorCode.POST_ID_INVALID));
        Comment parentComment = null;
        if (request.getParentCommentId() != null) {
            parentComment = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new AppException(ErrorCode.PARENT_COMMENT_NOT_EXISTED));
        }
        if (request.getContent() == null || request.getContent().isEmpty()) {
            throw new AppException(ErrorCode.CONTENT_COMMENT_INVALID);
        }
        if (bannedWordsService.containsBannedWords(request.getContent())) {
            throw new AppException(ErrorCode.CONTENT_COMMENT_INVALID);
        }
        Comment comment = Comment.builder()
                .content(request.getContent())
                .user(user)
                .post(post)
                .parentComment(parentComment)
                .build();
        commentRepository.save(comment);
        return commentMapper.toCommentResponse(comment);
    }

    @PreAuthorize("isAuthenticated()")
    public void deleteComment(Long commentId) {

        String email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_INVALID));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_EXISTED));

        if (Objects.equals(user.getId(), comment.getUser().getId()) ||
                PredefinedRole.ADMIN_ROLE.equals(user.getRole().getName())) {
            commentRepository.delete(comment);
            return;
        }

        throw new AppException(ErrorCode.DELETE_COMMENT_INVALID);
    }

    @PreAuthorize("isAuthenticated()")
    public UpdateCommentResponse updateComment(Long commentId, UpdateCommentRequest request) {

        if (commentId <= 0) {
            throw new AppException(ErrorCode.INVALID_PATH_VARIABLE_ID);
        }

        String email = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_INVALID));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_EXISTED));

        if (!Objects.equals(user.getId(), comment.getUser().getId())) {
            throw new AppException(ErrorCode.UPDATE_COMMENT_INVALID);
        }

        commentMapper.updateComment(request, comment);
        commentRepository.save(comment);

        return UpdateCommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .build();
    }
}
