package com.ru.microservice.service;

import com.ru.microservice.model.Message;

import java.time.LocalDate;
import java.util.List;

public interface MessageService {

    Message save(Message message);

    List<Message> getAll();

    List<Message> findByDate(LocalDate createdDate);

    List<Message> findMessagesByCreatedDate(LocalDate createdDate);

    List<Message> findMessagesByCreatedDateAndCreatedTime(LocalDate createdDate, LocalDate createdTime);
}