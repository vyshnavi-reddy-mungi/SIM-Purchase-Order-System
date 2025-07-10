package com.capgemini.telecom.ordersystem.kafka;
import com.capgemini.telecom.ordersystem.dto.SimCardEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.messaging.Message;

@Service
@Slf4j
public class SimCardProducer {

    private NewTopic purchaseTopic;
    private NewTopic activateTopic;
    private NewTopic suspendTopic;
    private NewTopic cancelTopic;

    private KafkaTemplate<String, SimCardEvent> kafkaTemplate;

    public SimCardProducer(
            NewTopic purchaseTopic,
            NewTopic activateTopic,
            NewTopic suspendTopic,
            NewTopic cancelTopic,
            KafkaTemplate<String, SimCardEvent> kafkaTemplate) {
        this.purchaseTopic = purchaseTopic;
        this.activateTopic = activateTopic;
        this.suspendTopic = suspendTopic;
        this.cancelTopic = cancelTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPurchaseMessage(SimCardEvent event) {
        log.info("Purchase SimCard event => {}", event);
        sendMessage(event, purchaseTopic.name());
    }

    public void sendActivateMessage(SimCardEvent event) {
        log.info("Activate SimCard event => {}", event);
        sendMessage(event, activateTopic.name());
    }

    public void sendSuspendMessage(SimCardEvent event) {
        log.info("Suspend SimCard event => {}", event);
        sendMessage(event, suspendTopic.name());
    }

    public void sendCancelMessage(SimCardEvent event) {
        log.info("Cancel SimCard event => {}", event);
        sendMessage(event, cancelTopic.name());
    }

    private void sendMessage(SimCardEvent event, String topicName) {
        Message<SimCardEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();
        kafkaTemplate.send(message);
    }

}
