package com.sadiqov.tour_manager_app.service.adminAndApi;

import com.sadiqov.tour_manager_app.dto.request.FeatureRequest;
import com.sadiqov.tour_manager_app.dto.response.FeatureResponse;
import com.sadiqov.tour_manager_app.entity.feature.Feature;
import com.sadiqov.tour_manager_app.mapper.FeatureMapper;
import com.sadiqov.tour_manager_app.repository.FeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeatureService {
    private final FeatureRepository featureRepository;
    private final FeatureMapper featureMapper;
    private static final String UPLOAD_DIR = "uploads/features/";

    public List<FeatureResponse> getAllFeatures(String lang) {
        return featureMapper.toResponseList(featureRepository.findAllByOrderByRowOrderAsc(), lang);
    }

    public FeatureResponse getFeatureById(Long id, String lang) {
        Feature feature = featureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Feature not found with id: " + id));
        return featureMapper.toResponse(feature, lang);
    }

    @Transactional
    public void createFeature(FeatureRequest request) {
        Feature feature = featureMapper.toEntity(request);

        if (request.image() != null && !request.image().isEmpty()) {
            String imagePath = saveImage(request.image());
            feature.setImagePath(imagePath);
        }

        featureRepository.save(feature);
    }

    @Transactional
    public void updateFeature(Long id, FeatureRequest request) {
        Feature feature = featureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Feature not found with id: " + id));

        feature.setTitleAz(request.titleAz());
        feature.setTitleEn(request.titleEn());
        feature.setTitleRu(request.titleRu());
        feature.setTextAz(request.textAz());
        feature.setTextEn(request.textEn());
        feature.setTextRu(request.textRu());
        feature.setRowOrder(request.rowOrder());

        if (request.image() != null && !request.image().isEmpty()) {
            if (feature.getImagePath() != null) {
                deleteImage(feature.getImagePath());
            }
            String imagePath = saveImage(request.image());
            feature.setImagePath(imagePath);
        }

        featureRepository.save(feature);
    }

    @Transactional
    public void deleteFeature(Long id) {
        Feature feature = featureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Feature not found with id: " + id));

        if (feature.getImagePath() != null) {
            deleteImage(feature.getImagePath());
        }

        featureRepository.delete(feature);
    }

    private String saveImage(MultipartFile image) {
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);

            Files.write(filePath, image.getBytes());
            return UPLOAD_DIR + fileName;

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save image: " + e.getMessage());
        }
    }

    private void deleteImage(String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete image: " + e.getMessage());
        }
    }
}