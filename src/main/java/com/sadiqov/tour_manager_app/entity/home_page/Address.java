package com.sadiqov.tour_manager_app.entity.home_page;

import com.sadiqov.tour_manager_app.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "addresses")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Address extends BaseEntity<Long> {

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