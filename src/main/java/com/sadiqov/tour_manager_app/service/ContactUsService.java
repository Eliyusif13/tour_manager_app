package com.sadiqov.tour_manager_app.service;

import com.sadiqov.tour_manager_app.dto.request.*;
import com.sadiqov.tour_manager_app.dto.response.ContactUsResponse;
import com.sadiqov.tour_manager_app.entity.home_page.ContactUs;
import com.sadiqov.tour_manager_app.entity.home_page.Subscriber;
import com.sadiqov.tour_manager_app.mapper.ContactUsMapper;
import com.sadiqov.tour_manager_app.repository.ContactUsRepository;
import com.sadiqov.tour_manager_app.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactUsService {
    private final ContactUsRepository contactUsRepository;
    private final ContactUsMapper contactUsMapper;
    private final SubscriberRepository subscriberRepository;

    public List<ContactUsResponse> getAllContactRequests() {
        return contactUsMapper.toResponseList(contactUsRepository.findAll());
    }

    public List<ContactUsResponse> getUnrespondedContactRequests() {
        return contactUsMapper.toResponseList(contactUsRepository.findByIsRespondedFalse());
    }

    public Optional<ContactUsResponse> getContactRequestById(Long id) {
        return contactUsRepository.findById(id)
                .map(contactUsMapper::toResponse);
    }

    @Transactional
    public void createContactRequest(ContactUsRequest request) {
        validateSubscription(request.email());
        checkForDuplicateRequests(request.email());

        ContactUs contactUs = contactUsMapper.toEntity(request);
        contactUsRepository.save(contactUs);
    }

    @Transactional
    public void updateContactRequest(Long id, ContactUsUpdateRequest request) {
        ContactUs contactUs = contactUsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact request not found"));

        contactUsMapper.updateEntityFromRequest(request, contactUs);
        contactUsRepository.save(contactUs);
    }

    @Transactional
    public void markAsResponded(Long id) {
        ContactUs contactUs = contactUsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact request not found"));
        contactUs.setIsResponded(true);
        contactUsRepository.save(contactUs);
    }

    @Transactional
    public void deleteContactRequest(Long id) {
        if (!contactUsRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact request not found");
        }
        contactUsRepository.deleteById(id);
    }

    public long getUnrespondedCount() {
        return contactUsRepository.countByIsRespondedFalse();
    }

    private void validateSubscription(String email) {
        Optional<Subscriber> subscriber = subscriberRepository.findByEmail(email);

        if (subscriber.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Bu email ilə abunəliyiniz yoxdur. Zəhmət olmasa əvvəlcə abunə olun.");
        }

        if (!subscriber.get().getIsActive()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Abunəliyiniz aktiv deyil. Zəhmət olmasa abunəliyi aktivləşdirin.");
        }
    }

    private void checkForDuplicateRequests(String email) {
        LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);
        boolean hasRecentRequest = contactUsRepository.existsByEmailAndCreatedAtAfter(email, twentyFourHoursAgo);

        if (hasRecentRequest) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Bu email ilə son 24 saatda artıq müraciət göndərilib. Zəhmət olmasa bir qədər gözləyin.");
        }
    }
}