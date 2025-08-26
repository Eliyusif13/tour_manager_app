package com.sadiqov.tour_manager_app.service;

import com.sadiqov.tour_manager_app.dto.DTORecords;
import com.sadiqov.tour_manager_app.dto.DTORecords.DemoAppealRequest;
import com.sadiqov.tour_manager_app.entity.DemoAppeal;
import com.sadiqov.tour_manager_app.entity.PhoneNumber;
import com.sadiqov.tour_manager_app.entity.Address;
import com.sadiqov.tour_manager_app.mapper.AddressMapper;
import com.sadiqov.tour_manager_app.mapper.DemoAppealMapper;
import com.sadiqov.tour_manager_app.mapper.PhoneNumberMapper;
import com.sadiqov.tour_manager_app.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DemoAppealService {
    private final DemoAppealRepository demoAppealRepository;
    private final PhoneNumberMapper phoneNumberMapper;
    private final AddressRepository addressRepository;
    private final DemoAppealMapper demoAppealMapper;
    private final AddressMapper addressMapper;

    @Transactional
    public void createDemoAppeal(DemoAppealRequest request) {
        DemoAppeal demoAppeal = demoAppealMapper.toEntity(request);

        if (request.phoneNumbers() != null) {
            for (DTORecords.PhoneNumberRequest phoneRequest : request.phoneNumbers()) {
                PhoneNumber phoneNumber = new PhoneNumber();
                phoneNumber.setPhoneNumber(phoneRequest.phoneNumber());
                demoAppeal.addPhoneNumber(phoneNumber);
            }
        }

        if (request.address() != null) {
            Address address = new Address();
            address.setAddressAz(request.address().addressAz());
            address.setAddressEn(request.address().addressEn());
            address.setAddressRu(request.address().addressRu());
            demoAppeal.setAddress(address);
        }

        demoAppealMapper.toResponse(demoAppealRepository.save(demoAppeal));
    }

    @Transactional
    public void updateDemoAppeal(Long id, DemoAppealRequest request) {
        DemoAppeal demoAppeal = demoAppealRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demo appeal not found"));

        demoAppeal.setEmail(request.email());

        demoAppeal.getPhoneNumbers().clear();
        if (request.phoneNumbers() != null) {
            for (var phoneRequest : request.phoneNumbers()) {
                PhoneNumber phoneNumber = phoneNumberMapper.toEntity(phoneRequest);
                phoneNumber.setDemoAppeal(demoAppeal);
                demoAppeal.addPhoneNumber(phoneNumber);
            }
        }

        if (request.address() != null) {
            if (demoAppeal.getAddress() != null) {
                Address existingAddress = demoAppeal.getAddress();
                existingAddress.setAddressAz(request.address().addressAz());
                existingAddress.setAddressEn(request.address().addressEn());
                existingAddress.setAddressRu(request.address().addressRu());
            } else {
                Address newAddress = addressMapper.toEntity(request.address());
                newAddress.setDemoAppeal(demoAppeal);
                demoAppeal.setAddress(newAddress);
            }
        } else {
            if (demoAppeal.getAddress() != null) {
                addressRepository.delete(demoAppeal.getAddress());
                demoAppeal.setAddress(null);
            }
        }

        demoAppealMapper.toResponse(demoAppealRepository.save(demoAppeal));
    }

    @Transactional
    public void deleteDemoAppeal(Long id) {
        DemoAppeal demoAppeal = demoAppealRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demo appeal not found"));

        demoAppeal.getPhoneNumbers().clear();

        demoAppealRepository.delete(demoAppeal);
    }
}