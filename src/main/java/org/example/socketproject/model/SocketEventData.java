package org.example.socketproject.model;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.*;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SocketEventData {
    private String eventName;
    private String room;
    private String userKey;
    private SocketIOClient senderClient;
}
