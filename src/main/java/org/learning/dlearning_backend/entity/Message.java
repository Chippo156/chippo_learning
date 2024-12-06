package org.learning.dlearning_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.learning.dlearning_backend.common.StatusChat;

@Entity
@Table(name = "messages")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message extends AbstractEntity<Long>{

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender", nullable = false)
    User sender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiver", nullable = false)
    User receiver;

    @Column(name = "content", nullable = false, columnDefinition = "MEDIUMTEXT")
    String content;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    StatusChat status;
}
