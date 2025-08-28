package com.sadiqov.tour_manager_app.entity.feature;

import com.sadiqov.tour_manager_app.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "features")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@EqualsAndHashCode(callSuper = true)
public class Feature extends BaseEntity<Long> {

    @Column(name = "title_az")
    String titleAz;

    @Column(name = "title_en")
    String titleEn;

    @Column(name = "title_ru")
    String titleRu;

    @Column(name = "text_az", columnDefinition = "TEXT")
    String textAz;

    @Column(name = "text_en", columnDefinition = "TEXT")
    String textEn;

    @Column(name = "text_ru", columnDefinition = "TEXT")
    String textRu;

    @Column(name = "image_path")
    String imagePath;

    @Transient
    MultipartFile image;

    @Column(name = "row_order")
    Integer rowOrder;
}