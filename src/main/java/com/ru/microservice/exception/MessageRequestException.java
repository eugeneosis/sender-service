package com.ru.microservice.exception;

public class MessageRequestException extends RuntimeException {

    public MessageRequestException(String message) {
        super(message);
    }
}