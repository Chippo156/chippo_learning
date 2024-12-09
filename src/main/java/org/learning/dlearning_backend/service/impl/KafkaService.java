package org.learning.dlearning_backend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.dto.event.CertificateCreationEvent;
import org.learning.dlearning_backend.dto.event.NotificationEvent;
import org.learning.dlearning_backend.dto.response.CertificateResponse;
import org.learning.dlearning_backend.entity.Certificate;
import org.learning.dlearning_backend.exception.AppException;
import org.learning.dlearning_backend.exception.ErrorCode;
import org.learning.dlearning_backend.repository.CertificateRepository;
import org.learning.dlearning_backend.repository.CourseRepository;
import org.learning.dlearning_backend.repository.UserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Slf4j
public class KafkaService {

    UserRepository userRepository;
    CourseRepository courseRepository;
    CertificateRepository certificateRepository;
    KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "certificate-creation", groupId = "certificate-group")
    public CertificateResponse createCertificate(CertificateCreationEvent event){
        log.info("Processing certificate creation for userId: {}, courseId: {}", event.getUserId(), event.getCourseId());

        Certificate certificate = Certificate.builder()
                .name("DLearning Certificate of Completion")
                .user(userRepository.findById(event.getUserId()).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED)))
                .course(courseRepository.findById(event.getCourseId()).orElseThrow(()-> new AppException(ErrorCode.COURSE_NOT_EXISTED)))
                .issueDate(java.time.LocalDateTime.now())
                .build();
        certificateRepository.save(certificate);
        Map<String ,Object> data = new HashMap<>();

        NotificationEvent event1 = NotificationEvent.builder()
                .channel("EMAIL")
                .subject("DLearning Certificate of Completion")
                .recipient(certificate.getUser().getEmail())
                .templateCode("certificate-template")
                .param(data)
                .build();
        kafkaTemplate.send("notification-delivery", event);
        return CertificateResponse.builder()
                .certificateId(certificate.getId())
                .email(certificate.getUser().getEmail())
                .courseName(certificate.getCourse().getTitle())
                .author(certificate.getCourse().getAuthor().getName())
                .certificateUrl(certificate.getCertificateUrl())
                .issueDate(certificate.getIssueDate())
                .build();
    }
}
