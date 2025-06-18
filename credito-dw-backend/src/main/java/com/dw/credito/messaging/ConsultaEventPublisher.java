package com.dw.credito.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConsultaEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    // Nome do tópico Kafka
    private static final String TOPICO_CONSULTAS = "consultas-audit";

    public ConsultaEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void publicarEvento(String mensagem) {
        kafkaTemplate.send(TOPICO_CONSULTAS, mensagem);
    }
}

