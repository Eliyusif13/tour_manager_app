package com.sadiqov.tour_manager_app.entity.faq_entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "faqs")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class FAQ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Question in Azerbaijani is required")
    @Column(name = "question_az", columnDefinition = "TEXT", nullable = false)
    String questionAz;

    @NotBlank(message = "Question in Russian is required")
    @Column(name = "question_ru", columnDefinition = "TEXT", nullable = false)
    String questionRu;

    @NotBlank(message = "Question in English is required")
    @Column(name = "question_en", columnDefinition = "TEXT", nullable = false)
    String questionEn;

    @NotBlank(message = "Answer in Azerbaijani is required")
    @Column(name = "answer_az", columnDefinition = "TEXT", nullable = false)
    String answerAz;

    @NotBlank(message = "Answer in Russian is required")
    @Column(name = "answer_ru", columnDefinition = "TEXT", nullable = false)
    String answerRu;

    @NotBlank(message = "Answer in English is required")
    @Column(name = "answer_en", columnDefinition = "TEXT", nullable = false)
    String answerEn;
}
