package org.learning.dlearning_backend.configuration;

import org.learning.dlearning_backend.utils.SecurityUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl  implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // Trả về tên người dùng hiện tại (hoặc bất kỳ giá trị nào bạn cần)


        String username = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println("username: " + username);
        return Optional.of(username); // Hoặc lấy từ SecurityContextHolder
    }
}
