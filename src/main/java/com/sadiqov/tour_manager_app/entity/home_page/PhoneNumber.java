package com.sadiqov.tour_manager_app.entity.home_page;

import com.sadiqov.tour_manager_app.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "phone_numbers")
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class PhoneNumber extends BaseEntity<Long> {

    @NotBlank(message = "Phone number is required")
    @Column(name = "phone_number", nullable = false)
    String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demo_appeal_id")
    DemoAppeal demoAppeal;
}