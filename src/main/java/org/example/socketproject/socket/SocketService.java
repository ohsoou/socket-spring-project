package org.example.socketproject.socket;

import com.corundumstudio.socketio.ClientOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.socketproject.domain.SocketData;
import org.example.socketproject.service.MonitoringService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SocketService {
    private final MonitoringService monitoringService;

    public void addViewer(String eventName, SocketIOClient client) {
        // namespace
        SocketIONamespace namespace = client.getNamespace();
        // room
        String room = client.getHandshakeData().getSingleUrlParam("room");
        client.joinRoom(room);
        // user
        String userId = client.get("userKey");

        String redisKey = String.join(":", namespace.getName(), room);

        List<String> viewers = monitoringService.getViewers(redisKey);
        viewers.add(userId);

        log.info(">> add:: saveViewer: {}: {}", namespace.getName(), userId);
        int count = monitoringService.saveViewerData(
                SocketData.builder()
                        .id(redisKey)
                        .userKeys(viewers).build()
        );

        namespace
                .getRoomOperations(room)
                .sendEvent(eventName, String.valueOf(count));
    }

    public void removeViewer(String eventName, SocketIOClient client) {
        // namespace
        SocketIONamespace namespace = client.getNamespace();
        // room
        String room = client.get("room");
        client.leaveRoom(room);
        // user
        String userId = client.get("userKey");

        String redisKey = String.join(":", namespace.getName(), room);

        List<String> viewers = monitoringService.getViewers(redisKey);
        viewers.remove(userId);

        log.info(">> remove:: removeViewer: {}: {}", namespace.getName(), userId);
        int count = monitoringService.saveViewerData(
                SocketData.builder()
                        .id(redisKey)
                        .userKeys(viewers).build());

        namespace
                .getRoomOperations(room)
                .sendEvent(eventName, String.valueOf(count));
    }

    public void initSocket(SocketIOServer server) {
        monitoringService.initRedisViewer();
        server.getAllClients().forEach(ClientOperations::disconnect);

    }
}
