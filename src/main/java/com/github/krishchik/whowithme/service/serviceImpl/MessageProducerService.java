package com.github.krishchik.whowithme.service.serviceImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageProducerService {

    @Value("${spring.rabbitmq.exchange}")
    private String mailExchange;
    @Value("${spring.rabbitmq.routingkey}")
    private String mailRoutingKey;

    private AmqpTemplate rabbitTemplate;

    public MessageProducerService(AmqpTemplate rabbitTemplate) {

        this.rabbitTemplate = rabbitTemplate;
    }
    public void sendUserRegistrationMessage() {

        rabbitTemplate.convertAndSend(mailExchange, mailRoutingKey, "userDto");
        log.info("User registration message was sent to mail sender service");
    }


}
