package com.sadiqov.tour_manager_app.mapper;

import com.sadiqov.tour_manager_app.dto.request.FAQRequest;
import com.sadiqov.tour_manager_app.dto.response.FAQResponse;
import com.sadiqov.tour_manager_app.entity.faq_entity.FAQ;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FAQMapper {

    FAQ toEntity(FAQRequest request);

    @Named("toAzResponse")
    default FAQResponse toAzResponse(FAQ faq) {
        return new FAQResponse(faq.getId(), faq.getQuestionAz(), faq.getAnswerAz());
    }

    @Named("toRuResponse")
    default FAQResponse toRuResponse(FAQ faq) {
        return new FAQResponse(faq.getId(), faq.getQuestionRu(), faq.getAnswerRu());
    }

    @Named("toEnResponse")
    default FAQResponse toEnResponse(FAQ faq) {
        return new FAQResponse(faq.getId(), faq.getQuestionEn(), faq.getAnswerEn());
    }

    default FAQResponse toResponse(FAQ faq, String lang) {
        return switch (lang.toLowerCase()) {
            case "ru" -> toRuResponse(faq);
            case "en" -> toEnResponse(faq);
            default -> toAzResponse(faq);
        };
    }

    default List<FAQResponse> toResponseList(List<FAQ> faqs, String lang) {
        return faqs.stream()
                .map(faq -> toResponse(faq, lang))
                .toList();
    }
}