package com.sadiqov.tour_manager_app.service;

import com.sadiqov.tour_manager_app.dto.DTORecords;
import com.sadiqov.tour_manager_app.dto.DTORecords.DemoAppealRequest;
import com.sadiqov.tour_manager_app.dto.DTORecords.DemoAppealResponse;
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
    public DemoAppealResponse createDemoAppeal(DemoAppealRequest request) {
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

        return demoAppealMapper.toResponse(demoAppealRepository.save(demoAppeal));
    }

    @Transactional
    public DemoAppealResponse updateDemoAppeal(Long id, DemoAppealRequest request) {
        DemoAppeal demoAppeal = demoAppealRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demo appeal not found"));

        // Əsas məlumatların yenilənməsi
        demoAppeal.setEmail(request.email());

        // PhoneNumber-ların yenilənməsi
        demoAppeal.getPhoneNumbers().clear();
        if (request.phoneNumbers() != null) {
            for (var phoneRequest : request.phoneNumbers()) {
                PhoneNumber phoneNumber = phoneNumberMapper.toEntity(phoneRequest);
                phoneNumber.setDemoAppeal(demoAppeal);
                demoAppeal.addPhoneNumber(phoneNumber);
            }
        }

        // Address-in yenilənməsi - DÜZƏLİŞ EDİLDİ
        if (request.address() != null) {
            if (demoAppeal.getAddress() != null) {
                // Mövcud addressi yenilə
                Address existingAddress = demoAppeal.getAddress();
                existingAddress.setAddressAz(request.address().addressAz());
                existingAddress.setAddressEn(request.address().addressEn());
                existingAddress.setAddressRu(request.address().addressRu());
            } else {
                // Yeni address yarat
                Address newAddress = addressMapper.toEntity(request.address());
                newAddress.setDemoAppeal(demoAppeal);
                demoAppeal.setAddress(newAddress);
            }
        } else {
            // Address null-dırsa, mövcud addressi sil
            if (demoAppeal.getAddress() != null) {
                addressRepository.delete(demoAppeal.getAddress());
                demoAppeal.setAddress(null);
            }
        }

        return demoAppealMapper.toResponse(demoAppealRepository.save(demoAppeal));
    }

    @Transactional
    public void deleteDemoAppeal(Long id) {
        DemoAppeal demoAppeal = demoAppealRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demo appeal not found"));

        demoAppeal.getPhoneNumbers().clear(); // Orphan removal işləməsi üçün

        demoAppealRepository.delete(demoAppeal);
    }
}