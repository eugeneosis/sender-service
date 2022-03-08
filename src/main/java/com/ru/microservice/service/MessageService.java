package com.ru.microservice.service;

import com.ru.microservice.model.Message;

import java.time.LocalDate;
import java.util.List;

public interface MessageService {

    Message save(Message message);

    List<Message> getAll();

    List<Message> findByDate(LocalDate date);
}