package com.sadiqov.tour_manager_app.repository;


import com.sadiqov.tour_manager_app.entity.DemoAppeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoAppealRepository extends JpaRepository<DemoAppeal, Long> {
    long count();
}
