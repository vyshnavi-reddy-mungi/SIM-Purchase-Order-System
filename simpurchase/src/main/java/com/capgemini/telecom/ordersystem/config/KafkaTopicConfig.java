package com.capgemini.telecom.ordersystem.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topic.purchase.name}")
    private String purchaseTopicName;

    @Value("${spring.kafka.topic.activate.name}")
    private String activateTopicName;

    @Value("${spring.kafka.topic.suspend.name}")
    private String suspendTopicName;

    @Value("${spring.kafka.topic.cancel.name}")
    private String cancelTopicName;

    @Bean
    public NewTopic purchaseTopic() {
        return TopicBuilder.name(purchaseTopicName).build();
    }

    @Bean
    public NewTopic activateTopic() {
        return TopicBuilder.name(activateTopicName).build();
    }

    @Bean
    public NewTopic suspendTopic() {
        return TopicBuilder.name(suspendTopicName).build();
    }

    @Bean
    public NewTopic cancelTopic() {
        return TopicBuilder.name(cancelTopicName).build();
    }
}
