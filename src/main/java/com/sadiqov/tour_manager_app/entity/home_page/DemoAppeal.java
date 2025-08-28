package com.sadiqov.tour_manager_app.entity.home_page;

import com.sadiqov.tour_manager_app.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "demo_appeals")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@EqualsAndHashCode(callSuper = true)
public class DemoAppeal extends BaseEntity<Long> {

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

    @OneToMany(mappedBy = "demoAppeal", cascade = CascadeType.ALL, orphanRemoval = true)
    List<PhoneNumber> phoneNumbers = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    Address address;

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