package org.learning.dlearning_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostCreationRequest {

    @NotBlank(message = "CONTENT_POST_INVALID")
    String content;

    String image;

}
