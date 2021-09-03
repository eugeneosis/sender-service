package com.ru.microservice.controller;

import com.ru.microservice.configuration.MQConfig;
import com.ru.microservice.model.Message;
import com.ru.microservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final MessageService messageService;
    private final RabbitTemplate template;

    public static final String API_MESSAGES = "api/messages";

    @PostMapping(API_MESSAGES)
    public ResponseEntity<Message> create(@RequestBody Message message) {
        messageService.save(message);
        log.info("Received new message: {}", message);
        template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, message);
        log.info("Sent message to queue");
        return ResponseEntity.ok().body(message);
    }

    @GetMapping(API_MESSAGES)
    public ResponseEntity<List<Message>> getAllMessages() {
        log.info("Fetch all messages");
        return ResponseEntity.ok().body(messageService.findAll());
    }
}