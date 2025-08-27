package com.sadiqov.tour_manager_app.entity.home_page;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "demo_appeals")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class DemoAppeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Full name is required")
    @Column(name = "full_name", nullable = false)
    String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    String email;

    @Column(name = "company_name")
    String companyName;

    @Column(name = "message", length = 1000)
    String message;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @OneToMany(mappedBy = "demoAppeal", cascade = CascadeType.ALL, orphanRemoval = true)
    List<PhoneNumber> phoneNumbers = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    Address address;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public void addPhoneNumber(PhoneNumber phoneNumber) {
        phoneNumbers.add(phoneNumber);
        phoneNumber.setDemoAppeal(this);
    }


    public void setAddress(Address address) {
        this.address = address;
        if (address != null) {
            address.setDemoAppeal(this);
        }
    }
}