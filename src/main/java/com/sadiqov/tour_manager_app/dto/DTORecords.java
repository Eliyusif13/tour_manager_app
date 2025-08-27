package com.sadiqov.tour_manager_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class DTORecords {

    //HOME PAGE RECORDS
    public record SubscriberResponse(
            Long id,
            String email,
            LocalDateTime createdAt,
            Boolean isActive
    ) {
    }

    public record SubscriberRequest(
            @NotBlank(message = "Email is required")
            @Email(message = "Please provide a valid email address")
            String email
    ) {
    }

    public record PhoneNumberResponse(
            Long id,
            String phoneNumber,
            Long demoAppealId
    ) {
    }

    public record PhoneNumberRequest(
            @NotBlank(message = "Phone number is required")
            String phoneNumber
    ) {
    }

    public record AddressResponse(
            Long id,
            String addressAz,
            String addressEn,
            String addressRu,
            Long demoAppealId
    ) {
    }

    public record AddressRequest(
            String addressAz,
            String addressEn,
            String addressRu
    ) {
    }

    public record DemoAppealRequest(
            @NotBlank(message = "Full name is required")
            String fullName,

            @NotBlank(message = "Email is required")
            @Email(message = "Please provide a valid email address")
            String email,

            String companyName,
            String message,

            List<PhoneNumberRequest> phoneNumbers,
            AddressRequest address
    ) {
    }

    public record DemoAppealResponse(
            Long id,
            String fullName,
            String email,
            String companyName,
            String message,
            LocalDateTime createdAt,
            List<PhoneNumberResponse> phoneNumbers,
            AddressResponse address
    ) {
    }

    public record ContactUsResponse(
            Long id,
            String name,
            String email,
            String message,
            LocalDateTime createdAt,
            Boolean isResponded
    ) {
    }

    public record ContactUsRequest(
            @NotBlank(message = "Name is required")
            String name,

            @NotBlank(message = "Email is required")
            @Email(message = "Please provide a valid email address")
            String email,

            @NotBlank(message = "Message is required")
            String message
    ) {
    }

    public record ContactUsUpdateRequest(
            @NotBlank(message = "Name is required")
            String name,

            @NotBlank(message = "Email is required")
            @Email(message = "Please provide a valid email address")
            String email,

            @NotBlank(message = "Message is required")
            String message,

            Boolean isResponded
    ) {
    }

    //FAQ RECORDS

    public record FAQResponse(
            Long id,
            String question,
            String answer
    ) {
    }

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
    ) {
    }
}