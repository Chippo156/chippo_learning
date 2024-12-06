package org.learning.dlearning_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Role extends AbstractEntity<Long>{
    String name;
    String description;

    @OneToMany(mappedBy = "role")
    Set<User> users;
    @ManyToMany
    Set<Permission> permissions;

}
