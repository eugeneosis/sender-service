package com.ru.microservice.controller;

import com.ru.microservice.configuration.MQConfig;
import com.ru.microservice.exception.MessageRequestException;
import com.ru.microservice.model.Message;
import com.ru.microservice.service.MessageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class MessageController {

    private final MessageServiceImpl messageServiceImpl;
    private final RabbitTemplate template;

    private static final String API_MESSAGES = "/messages";
    private static final String API_BY_DATE = "/by-date";

    private static final String MESSAGE_EXCEPTION = "Message must not be empty";

    @PostMapping(API_MESSAGES)
    public ResponseEntity<Message> create(@RequestBody Message message) {
        validateMessage(message);
        messageServiceImpl.save(message);
        log.info("Received new message: {}", message.getMessage());
        template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, message);
        log.info("Sent message to queue");
        return ResponseEntity.ok().body(message);
    }

    @GetMapping(API_MESSAGES)
    public ResponseEntity<List<Message>> getAllMessages() {
        log.info("Fetch all messages");
        return ResponseEntity.ok(messageServiceImpl.getAll());
    }

    @GetMapping(API_BY_DATE)
    public ResponseEntity<List<Message>> getMessagesByDate(LocalDate date) {
        log.info("Fetching messages by date");
        return ResponseEntity.ok(messageServiceImpl.findByDate(date));
    }

    private void validateMessage(Message message) {
        if (message.getMessage().isEmpty() || message.getMessage() == null) {
            throw new MessageRequestException(MESSAGE_EXCEPTION);
        }
    }
}