package com.sadiqov.tour_manager_app.service.adminAndApi;

import com.sadiqov.tour_manager_app.dto.request.SubscriberRequest;
import com.sadiqov.tour_manager_app.dto.response.SubscriberResponse;
import com.sadiqov.tour_manager_app.entity.home_page.Subscriber;
import com.sadiqov.tour_manager_app.mapper.SubscriberMapper;
import com.sadiqov.tour_manager_app.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    @Transactional
    public void createSubscriber(SubscriberRequest request) {
        if (subscriberRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        Subscriber subscriber = subscriberMapper.toEntity(request);
        subscriberRepository.save(subscriber);
    }

    @Transactional
    public void deactivateSubscriber(Long id) {
        Subscriber subscriber = subscriberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscriber not found"));
        subscriber.setIsActive(false);
        subscriberRepository.save(subscriber);
    }

    @Transactional
    public void activateSubscriber(Long id) {
        Subscriber subscriber = subscriberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscriber not found"));
        subscriber.setIsActive(true);
        subscriberRepository.save(subscriber);
    }

    public long getActiveSubscribersCount() {
        return subscriberRepository.countByIsActiveTrue();
    }

    public long getAllSubscribersCount() {
        return subscriberRepository.count();
    }
}