package org.example.socketproject.model;

import lombok.*;
import org.example.socketproject.enums.MessageType;

@NoArgsConstructor
@Getter
@Builder
@ToString
public class Message {
    private MessageType type;
    private String message;

    public Message(MessageType messageType, String message) {
        this.type = messageType;
        this.message = message;
    }
}

