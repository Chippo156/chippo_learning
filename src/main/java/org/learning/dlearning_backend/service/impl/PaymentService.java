package org.learning.dlearning_backend.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.learning.dlearning_backend.repository.UserRepository;
import org.learning.dlearning_backend.utils.VNPayUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentService {
    UserRepository userRepository;
    VNPayUtils vnPayUtils;

}
