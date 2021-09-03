package com.ru.microservice.controller;

import com.ru.microservice.configuration.MQConfig;
import com.ru.microservice.dto.MessageDto;
import com.ru.microservice.dto.MessageDtoFactory;
import com.ru.microservice.model.Message;
import com.ru.microservice.repository.MessageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Transactional
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MessageDtoController {

    MessageDtoFactory messageDtoFactory;
    MessageRepository messageRepository;
    RabbitTemplate template;

    public static final String API_MESSAGES = "api/dto/messages";

    @PostMapping(API_MESSAGES)
    public MessageDto create(@RequestParam String message) {
        Message savedMessage = messageRepository.save(
                Message.builder()
                        .message(message)
                        .build()
        );
        log.info("Received new message: {}", message);

        template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, message);
        log.info("Sent message to queue");
        return messageDtoFactory.messageDto(savedMessage);
    }

    @GetMapping(API_MESSAGES)
    public List<Message> getAllMessages() {
        log.info("Get all messages");
        return messageRepository.findAll();
    }
}