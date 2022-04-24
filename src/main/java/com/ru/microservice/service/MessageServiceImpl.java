package com.ru.microservice.service;

import com.ru.microservice.model.Message;
import com.ru.microservice.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    @Transactional
    public Message save(Message message) {
        message.setCreatedDate(LocalDateTime.now().toLocalDate());
        message.setCreatedTime(LocalDateTime.now().toLocalTime());
        log.info("Saved new message: {}", message.getMessage());
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getAll() {
        log.info("Get all messages");
        return messageRepository.findAll();
    }

    @Override
    public List<Message> findByDate(LocalDate createdDate) {
        return messageRepository.findAllMessagesByCreatedDate(createdDate);
    }

    @Override
    public List<Message> findMessagesByCreatedDate(LocalDate date) {
        return messageRepository.findMessagesByCreatedDate(date);
    }

    @Override
    public List<Message> findMessagesByCreatedDateAndCreatedTime(LocalDate createdDate, LocalDate createdTime) {
        return messageRepository.findMessagesByCreatedDateAndCreatedTime(createdDate, createdTime);
    }
}