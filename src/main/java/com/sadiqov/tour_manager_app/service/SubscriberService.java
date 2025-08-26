package com.sadiqov.tour_manager_app.service;

import com.sadiqov.tour_manager_app.dto.DTORecords.SubscriberRequest;
import com.sadiqov.tour_manager_app.dto.DTORecords.SubscriberResponse;
import com.sadiqov.tour_manager_app.entity.Subscriber;
import com.sadiqov.tour_manager_app.mapper.SubscriberMapper;
import com.sadiqov.tour_manager_app.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;
    private final SubscriberMapper subscriberMapper;

    public List<SubscriberResponse> getAllSubscribers() {
        return subscriberMapper.toResponseList(subscriberRepository.findAll());
    }

    public Optional<SubscriberResponse> getSubscriberById(Long id) {
        return subscriberRepository.findById(id)
                .map(subscriberMapper::toResponse);
    }

    public SubscriberResponse createSubscriber(SubscriberRequest request) {
        if (subscriberRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }

        Subscriber subscriber = new Subscriber();
        subscriber.setEmail(request.email());
        subscriber.setCreatedAt(java.time.LocalDateTime.now());
        subscriber.setIsActive(true);

        return subscriberMapper.toResponse(subscriberRepository.save(subscriber));
    }

    public void deactivateSubscriber(Long id) {
        Subscriber subscriber = subscriberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));
        subscriber.setIsActive(false);
        subscriberRepository.save(subscriber);
    }

    public long getActiveSubscribersCount() {
        return subscriberRepository.countByIsActiveTrue();
    }
}