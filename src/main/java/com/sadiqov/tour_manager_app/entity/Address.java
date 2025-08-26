package com.sadiqov.tour_manager_app.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
@Entity
@Table(name = "addresses")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "address_az", length = 500)
    String addressAz;

    @Column(name = "address_en", length = 500)
    String addressEn;

    @Column(name = "address_ru", length = 500)
    String addressRu;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demo_appeal_id")
    DemoAppeal demoAppeal;
}