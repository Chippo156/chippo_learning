package org.learning.dlearning_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Post extends AbstractEntity<Long>{

    @Column(name = "title")
    String title;

    @Column(name = "content", nullable = false, columnDefinition = "MEDIUMTEXT ")
    String content;

    @Column(name = "image")
    String image;

    @Column(name = "like_count", columnDefinition = "INTEGER DEFAULT 0")
    Integer likeCount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Comment> comments;

    @PrePersist
    protected void onCreate() {
        if (this.likeCount == null) {
            this.likeCount = 0;
        }
    }

}
