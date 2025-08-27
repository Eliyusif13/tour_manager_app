package com.sadiqov.tour_manager_app.repository;


import com.sadiqov.tour_manager_app.entity.home_page.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    Optional<Subscriber> findByEmail(String email);
    boolean existsByEmail(String email);
    long countByIsActiveTrue();
}
