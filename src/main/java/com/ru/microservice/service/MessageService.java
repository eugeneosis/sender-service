package com.ru.microservice.service;

import com.ru.microservice.model.Message;
import com.ru.microservice.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;

    @Transactional
    public Message save(Message message) {
        message.setDateTime(Timestamp.valueOf(now()));
        log.info("Saved new message: {}", message);
        return messageRepository.save(message);
    }

    public List<Message> findAll() {
        log.info("Get all messages");
        return messageRepository.findAll();
    }
}
