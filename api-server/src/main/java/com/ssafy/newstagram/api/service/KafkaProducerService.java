package com.ssafy.newstagram.api.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<Object, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        try {
            kafkaTemplate.send(topic, message).get();
        } catch(Exception e) {
            System.err.println("============== [Kafka 전송 ERROR] ==============");
            System.err.println("발생 서버: api-server");
            System.err.println("토픽: " + topic);
            System.err.println("메시지: " + message);
            System.err.println("=====================================");
            e.printStackTrace();
        }
    }
}
