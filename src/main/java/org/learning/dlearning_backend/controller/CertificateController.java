package org.learning.dlearning_backend.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.dto.event.CertificateCreationEvent;
import org.learning.dlearning_backend.dto.response.ApiResponse;
import org.learning.dlearning_backend.dto.response.CertificateResponse;
import org.learning.dlearning_backend.service.impl.CertificateService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/certificate")
@Slf4j
public class CertificateController {
    CertificateService certificateService;

    @PostMapping("/creation")
    ApiResponse<Void> createCertification(@RequestBody CertificateCreationEvent request) {
        certificateService.createCertificate(request);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Certificate created")
                .build();
    }

    @GetMapping("/current-login")
    ApiResponse<List<CertificateResponse>> getCertificationByUserLogin() {
        return ApiResponse.<List<CertificateResponse>>builder()
                .code(HttpStatus.OK.value())
                .result(certificateService.getCertificateByUserLogin())
                .build();
    }
}
