package com.neoflex.deal.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neoflex.deal.dto.EmailMessage;
import com.neoflex.deal.enums.Theme;
import com.neoflex.deal.models.Application;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final ApplicationService applicationService;

    public void send(String topic, Theme theme, Long id) throws JsonProcessingException {
        Application app = applicationService.getApplication(id);

        EmailMessage emailMessage = EmailMessage.builder()
                .address(app.getClient().getEmail())
                .theme(theme)
                .applicationId(app.getId())
                .build();

        String json = objectMapper.writeValueAsString(emailMessage);

        kafkaTemplate.send(topic, json);
    }
}
