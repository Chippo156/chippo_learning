package org.learning.dlearning_backend.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.learning.dlearning_backend.mapper.RegisterTeacherMapper;
import org.learning.dlearning_backend.repository.RoleRepository;
import org.learning.dlearning_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RegisterTeacherService {

    RegisterTeacherMapper mapper;
    UserRepository userRepository;
    RoleRepository roleRepository;


}
