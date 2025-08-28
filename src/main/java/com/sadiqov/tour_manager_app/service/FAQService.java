package com.sadiqov.tour_manager_app.service;

import com.sadiqov.tour_manager_app.dto.request.FAQRequest;
import com.sadiqov.tour_manager_app.dto.response.FAQResponse;
import com.sadiqov.tour_manager_app.entity.faq_entity.FAQ;
import com.sadiqov.tour_manager_app.mapper.FAQMapper;
import com.sadiqov.tour_manager_app.repository.FAQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FAQService {
    private final FAQRepository faqRepository;
    private final FAQMapper faqMapper;

    public List<FAQResponse> getAllFAQs(String lang) {
        return faqMapper.toResponseList(faqRepository.findAll(), lang);
    }

    public Optional<FAQResponse> getFAQById(Long id, String lang) {
        return faqRepository.findById(id)
                .map(faq -> faqMapper.toResponse(faq, lang));
    }

    public void createFAQ(FAQRequest request) {
        FAQ faq = faqMapper.toEntity(request);
        FAQ savedFAQ = faqRepository.save(faq);
        faqMapper.toResponse(savedFAQ, "az");
    }

    public void updateFAQ(Long id, FAQRequest request) {
        FAQ existingFAQ = faqRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FAQ not found with id: " + id));

        existingFAQ.setQuestionAz(request.questionAz());
        existingFAQ.setQuestionRu(request.questionRu());
        existingFAQ.setQuestionEn(request.questionEn());
        existingFAQ.setAnswerAz(request.answerAz());
        existingFAQ.setAnswerRu(request.answerRu());
        existingFAQ.setAnswerEn(request.answerEn());

        FAQ updatedFAQ = faqRepository.save(existingFAQ);
        faqMapper.toResponse(updatedFAQ, "az");
    }

    public void deleteFAQ(Long id) {
        if (!faqRepository.existsById(id)) {
            throw new RuntimeException("FAQ not found with id: " + id);
        }
        faqRepository.deleteById(id);
    }
}