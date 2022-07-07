package com.neoflex.deal.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic finishRegistrationTopic() {
        return TopicBuilder.name("finish_registration").build();
    }

    @Bean
    public NewTopic createDocumentsTopic() {
        return TopicBuilder.name("create_documents").build();
    }

    @Bean
    public NewTopic sendDocumentsTopic() {
        return TopicBuilder.name("send_documents").build();
    }

    @Bean
    public NewTopic sendSesTopic() {
        return TopicBuilder.name("send_ses").build();
    }

    @Bean
    public NewTopic creditIssuedTopic() {
        return TopicBuilder.name("credit_issued").build();
    }

    @Bean
    public NewTopic applicationDeniedTopic() {
        return TopicBuilder.name("application_denied").build();
    }

}
