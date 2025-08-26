package com.sadiqov.tour_manager_app.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "contact_requests")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ContactUs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Name is required")
    String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    String email;

    @NotBlank(message = "Message is required")
    @Column(length = 1000)
    String message;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "is_responded")
    Boolean isResponded;

    public ContactUs() {
        this.createdAt = LocalDateTime.now();
        this.isResponded = false;
    }
}