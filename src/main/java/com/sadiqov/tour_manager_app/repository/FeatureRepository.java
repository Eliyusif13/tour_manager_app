package com.sadiqov.tour_manager_app.repository;

import com.sadiqov.tour_manager_app.entity.feature.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    List<Feature> findAllByOrderByRowOrderAsc();
}