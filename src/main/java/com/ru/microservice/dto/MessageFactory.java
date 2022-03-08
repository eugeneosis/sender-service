package com.ru.microservice.dto;

import org.springframework.stereotype.Component;

@Component
public class MessageFactory {

    public Message messageDto(com.ru.microservice.model.Message message) {
        return Message.builder()
                .id(message.getId())
                .message(message.getMessage())
                .date(message.getCreatedDateAt())
                .time(message.getCreatedTimeAt())
                .build();
    }
}