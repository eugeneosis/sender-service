package com.ru.microservice.repository;

import com.ru.microservice.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findAllByCreatedDateAt(LocalDate date);
}
