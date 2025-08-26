package com.sadiqov.tour_manager_app.repository;

import com.sadiqov.tour_manager_app.entity.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContactUsRepository extends JpaRepository<ContactUs, Long> {
    List<ContactUs> findByIsRespondedFalse();
    long countByIsRespondedFalse();

    @Query("SELECT COUNT(c) > 0 FROM ContactUs c WHERE c.email = :email AND c.createdAt > :time")
    boolean existsByEmailAndCreatedAtAfter(@Param("email") String email, @Param("time") LocalDateTime time);
}