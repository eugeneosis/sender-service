package com.ru.microservice.repository;

import com.ru.microservice.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query(value = "select * from messages order by date desc", nativeQuery = true)
    List<Message> findAllMessagesByCreatedDate(LocalDate createdDate);

    @Query(value = "Select m from Message as m order by m.createdDate desc")
    List<Message> findMessagesByCreatedDate(LocalDate createdDate);

    @Query(value = "Select m from Message as m order by m.createdDate desc, m.createdTime desc")
    List<Message> findMessagesByCreatedDateAndCreatedTime(LocalDate createdDate, LocalDate createdTime);
}
