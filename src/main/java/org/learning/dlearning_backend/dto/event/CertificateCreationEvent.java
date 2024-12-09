package org.learning.dlearning_backend.dto.event;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CertificateCreationEvent {
    Long userId;
    Long courseId;
}
