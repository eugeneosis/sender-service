package com.ru.microservice.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

import static java.time.LocalDateTime.now;

@Entity
@Table(name = "Messages")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "message", unique = true)
    String message;

    @Column(name = "date")
    @Builder.Default
    Timestamp dateTime = Timestamp.valueOf(now());
}