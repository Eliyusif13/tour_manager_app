package com.sadiqov.tour_manager_app.service.adminAndApi;

import com.sadiqov.tour_manager_app.dto.request.DemoAppealRequest;
import com.sadiqov.tour_manager_app.dto.response.DemoAppealResponse;
import com.sadiqov.tour_manager_app.dto.request.PhoneNumberRequest;
import com.sadiqov.tour_manager_app.entity.home_page.DemoAppeal;
import com.sadiqov.tour_manager_app.entity.home_page.PhoneNumber;
import com.sadiqov.tour_manager_app.entity.home_page.Address;
import com.sadiqov.tour_manager_app.mapper.DemoAppealMapper;
import com.sadiqov.tour_manager_app.repository.DemoAppealRepository;
import com.sadiqov.tour_manager_app.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DemoAppealService {
    private final DemoAppealRepository demoAppealRepository;
    private final AddressRepository addressRepository;
    private final DemoAppealMapper demoAppealMapper;

    @Transactional
    public void createDemoAppeal(DemoAppealRequest request) {
        DemoAppeal demoAppeal = demoAppealMapper.toEntity(request);

        if (request.phoneNumbers() != null) {
            for (PhoneNumberRequest phoneRequest : request.phoneNumbers()) {
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

        demoAppealRepository.save(demoAppeal);
    }

    @Transactional
    public void updateDemoAppeal(Long id, DemoAppealRequest request) {
        DemoAppeal demoAppeal = demoAppealRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Demo appeal not found"));

        demoAppeal.setFullName(request.fullName());
        demoAppeal.setEmail(request.email());
        demoAppeal.setCompanyName(request.companyName());
        demoAppeal.setMessage(request.message());

        demoAppeal.getPhoneNumbers().clear();
        if (request.phoneNumbers() != null) {
            for (var phoneRequest : request.phoneNumbers()) {
                PhoneNumber phoneNumber = new PhoneNumber();
                phoneNumber.setPhoneNumber(phoneRequest.phoneNumber());
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
                Address newAddress = new Address();
                newAddress.setAddressAz(request.address().addressAz());
                newAddress.setAddressEn(request.address().addressEn());
                newAddress.setAddressRu(request.address().addressRu());
                newAddress.setDemoAppeal(demoAppeal);
                demoAppeal.setAddress(newAddress);
            }
        } else {
            if (demoAppeal.getAddress() != null) {
                addressRepository.delete(demoAppeal.getAddress());
                demoAppeal.setAddress(null);
            }
        }

        demoAppealRepository.save(demoAppeal);
    }

    @Transactional
    public void deleteDemoAppeal(Long id) {
        DemoAppeal demoAppeal = demoAppealRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Demo appeal not found"));

        demoAppeal.getPhoneNumbers().clear();
        demoAppealRepository.delete(demoAppeal);
    }

    public DemoAppealResponse getDemoAppealById(Long id) {
        DemoAppeal demoAppeal = demoAppealRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Demo appeal not found"));
        return demoAppealMapper.toResponse(demoAppeal);
    }

    public List<DemoAppealResponse> getAllDemoAppeals() {
        return demoAppealMapper.toResponseList(demoAppealRepository.findAll());
    }
}