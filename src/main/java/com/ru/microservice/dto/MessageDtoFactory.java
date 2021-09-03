package com.ru.microservice.dto;

import com.ru.microservice.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageDtoFactory {

    public MessageDto messageDto(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .name(message.getMessage())
                .dateTime(message.getDateTime())
                .build();
    }
}