package com.sadiqov.tour_manager_app.entity.user;

import com.sadiqov.tour_manager_app.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity<Long> {

    @NotBlank(message = "Username is required")
    @Column(unique = true, nullable = false)
    String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(unique = true, nullable = false)
    String email;

    @NotBlank(message = "Password is required")
    String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Role role;

    @Column(name = "is_active")
    Boolean isActive = true;
}