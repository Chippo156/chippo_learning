package org.learning.dlearning_backend.entity;

import com.spring.dlearning.common.PaymentMethodName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class PaymentMethod extends AbstractEntity<Long> {

    @Column(name = "method_name", nullable = false)
    @Enumerated(EnumType.STRING)
    PaymentMethodName methodName;

    @Column(name = "details")
    String details;
}
