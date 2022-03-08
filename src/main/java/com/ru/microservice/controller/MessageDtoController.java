package com.ru.microservice.controller;

import com.ru.microservice.configuration.MQConfig;
import com.ru.microservice.dto.Message;
import com.ru.microservice.dto.MessageFactory;
import com.ru.microservice.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class MessageDtoController {

    private final MessageFactory messageFactory;
    private final MessageRepository messageRepository;
    private final RabbitTemplate template;

    private static final String API_MESSAGES = "/dto/messages";

    @PostMapping(API_MESSAGES)
    public Message create(@RequestParam String message) {
        com.ru.microservice.model.Message savedMessage = messageRepository.save(
                com.ru.microservice.model.Message.builder()
                        .message(message)
                        .build()
        );
        log.info("Received new message: {}", message);

        template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, savedMessage);

        log.info("Sent message to queue");
        return messageFactory.messageDto(savedMessage);
    }

    @GetMapping(API_MESSAGES)
    public List<com.ru.microservice.model.Message> getAllMessages() {
        log.info("Get all messages");
        return messageRepository.findAll();
    }
}