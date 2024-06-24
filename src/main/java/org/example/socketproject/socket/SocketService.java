package org.example.socketproject.socket;

import com.corundumstudio.socketio.ClientOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.socketproject.constants.SocketStoreKey;
import org.example.socketproject.model.SocketData;
import org.example.socketproject.service.MonitoringService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SocketService {
    private final MonitoringService monitoringService;

    public void addViewer(String eventName, SocketIOClient client) {
        // namespace
        SocketIONamespace namespace = client.getNamespace();
        // room
        String room = client.getHandshakeData().getSingleUrlParam(SocketStoreKey.ROOM);
        client.joinRoom(room);
        // user
        String userKey = client.get(SocketStoreKey.USER_KEY);

        String redisKey = String.join(":", namespace.getName(), room);
        SocketData socketData = SocketData.builder().namespace(redisKey).socketId(client.getSessionId().toString()).userKey(userKey).build();

        log.info(">> add:: saveViewer: {}: {}", namespace.getName(), userKey);
        monitoringService.saveViewerData(socketData);
        int count = monitoringService.getRealViewers(socketData);

        namespace
                .getRoomOperations(room)
                .sendEvent(eventName, String.valueOf(count));
    }

    public void removeViewer(String eventName, SocketIOClient client) {
        // namespace
        SocketIONamespace namespace = client.getNamespace();
        // room
        String room = client.get(SocketStoreKey.ROOM);
        client.leaveRoom(room);
        // user
        String userKey = client.get(SocketStoreKey.USER_KEY);

        String redisKey = String.join(":", namespace.getName(), room);
        SocketData socketData = SocketData.builder().namespace(redisKey).socketId(client.getSessionId().toString()).userKey(userKey).build();


        log.info(">> remove:: removeViewer: {}: {}", namespace.getName(), userKey);
        monitoringService.deleteViewerData(socketData);
        int count = monitoringService.getRealViewers(socketData);


        // Message to Client
        namespace
                .getRoomOperations(room)
                .sendEvent(eventName, String.valueOf(count));
    }

    public void initSocket(SocketIOServer server) {
        monitoringService.initRedisViewer();
        server.getAllClients().forEach(ClientOperations::disconnect);

    }
}
