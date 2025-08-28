package com.sadiqov.tour_manager_app.entity.home_page;

import com.sadiqov.tour_manager_app.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "contact_requests")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@EqualsAndHashCode(callSuper = true)
public class ContactUs extends BaseEntity<Long> {

    @NotBlank(message = "Name is required")
    String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    String email;

    @NotBlank(message = "Message is required")
    @Column(length = 1000)
    String message;

    @Column(name = "is_responded")
    Boolean isResponded;

    public ContactUs() {
        this.isResponded = false;
    }
}