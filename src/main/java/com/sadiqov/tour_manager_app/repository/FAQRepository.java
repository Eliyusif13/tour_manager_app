package com.sadiqov.tour_manager_app.repository;

import com.sadiqov.tour_manager_app.entity.faq_entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Long> {
}