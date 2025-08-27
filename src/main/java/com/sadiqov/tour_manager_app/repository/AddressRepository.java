package com.sadiqov.tour_manager_app.repository;

import com.sadiqov.tour_manager_app.entity.home_page.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository  extends JpaRepository<Address,Long> {
}
