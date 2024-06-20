package org.example.socketproject.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.socketproject.enums.MessageType;
import org.example.socketproject.model.Message;
import org.example.socketproject.model.SocketData;
import org.example.socketproject.model.SocketEventData;
import org.example.socketproject.service.MonitoringService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class SocketService {
    private final MonitoringService monitoringService;

    public void sendMessage(String eventName, SocketIOClient senderClient, String message ) {
        for(SocketIOClient client : senderClient.getNamespace().getAllClients()) {
            if(!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent(eventName, new Message(MessageType.SERVER, message));
            }
        }
    }

    public void addViewer(SocketEventData socketEventData) {
        SocketIONamespace namespace = socketEventData.getSenderClient().getNamespace();
        String userId = socketEventData.getUserKey();
        String id = String.join(":", namespace.getName(), socketEventData.getRoom());
        Set<String> viewers = monitoringService.getViewers(id);
        viewers.add(userId);
        log.info(">> add:: currentViewers: {}", viewers.toArray());
        log.info(">> add:: saveViewer: {}: {}", namespace.getName(), userId);
        int count = monitoringService.saveViewerData(
                SocketData.builder()
                        .id(id)
                        .userKeys(viewers).build()
        );

        namespace
                .getRoomOperations(socketEventData.getRoom())
                .sendEvent(socketEventData.getEventName(), String.valueOf(count));
    }

    public void removeViewer(SocketEventData socketEventData) {
        SocketIONamespace namespace = socketEventData.getSenderClient().getNamespace();
        String userId = socketEventData.getUserKey();
        String id = String.join(":", namespace.getName(), socketEventData.getRoom());

        Set<String> viewers = monitoringService.getViewers(id);
        viewers.remove(userId);
        log.info(">> remove:: currentViewers: {}", viewers.toArray());

        log.info(">> remove:: removeViewer: {}: {}", namespace.getName(), userId);
        int count = monitoringService.saveViewerData(
                SocketData.builder()
                        .id(id)
                        .userKeys(viewers).build());

        namespace
                .getRoomOperations(socketEventData.getRoom())
                .sendEvent(socketEventData.getEventName(), String.valueOf(count));
    }
}
