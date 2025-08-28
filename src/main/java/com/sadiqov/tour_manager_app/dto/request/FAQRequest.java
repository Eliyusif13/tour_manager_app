package com.sadiqov.tour_manager_app.dto.request;

import jakarta.validation.constraints.NotBlank;

public record FAQRequest(
        @NotBlank(message = "Question in Azerbaijani is required")
        String questionAz,

        @NotBlank(message = "Question in Russian is required")
        String questionRu,

        @NotBlank(message = "Question in English is required")
        String questionEn,

        @NotBlank(message = "Answer in Azerbaijani is required")
        String answerAz,

        @NotBlank(message = "Answer in Russian is required")
        String answerRu,

        @NotBlank(message = "Answer in English is required")
        String answerEn
) {}
