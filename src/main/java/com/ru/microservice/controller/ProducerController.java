package com.ru.microservice.controller;

import com.ru.microservice.configuration.MQConfig;
import com.ru.microservice.data.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.UUID;

import static java.time.LocalDateTime.now;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProducerController {

    private final RabbitTemplate template;

    public static final String API_PRODUCER = "api/producer";

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(API_PRODUCER)
    public String publishMessage(@RequestBody Message message) {
        message.setId(UUID.randomUUID().toString());
        message.setDateTime(Timestamp.valueOf(now()));
        template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, message);
        log.info("Created new message: {}", message);
        return "Message created successfully";
    }
}
