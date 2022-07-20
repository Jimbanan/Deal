package com.neoflex.deal.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.neoflex.deal.enums.Theme;

public interface KafkaProducerService {

    void send(String topic, Theme theme, Long id) throws JsonProcessingException;

}
