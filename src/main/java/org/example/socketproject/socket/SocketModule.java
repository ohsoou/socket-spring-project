package org.example.socketproject.socket;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.example.socketproject.model.SocketData;
import org.example.socketproject.model.SocketEventData;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketModule {
    private final SocketIOServer server;
    private final SocketService socketService;

    private static final String SERVER_EVENT_NAME = "getCount";

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());

        // namespace event
//        addNamespaceEventListener("/message", "send_message", Message.class, onMessageReceived());
//        addNamespaceEventListener("^\\/room/\\d+$", "addViewer", Message.class, onViewerReceived());
        addNamespaceEventListener("/room", "addViewer", SocketEventData.class, onViewerReceived());
        addNamespaceEventListener("/room", "leaveViewer", SocketEventData.class, onViewerLeaved());
    }

    private DataListener<SocketEventData> onViewerReceived() {
        return (senderClient, data, ackSender) -> {
            log.info(data.toString());
            String room = data.getRoom();
            senderClient.joinRoom(room);
            socketService.addViewer(SocketEventData.builder().eventName(SERVER_EVENT_NAME).room(room).senderClient(senderClient).userKey(data.getUserKey()).build());
        };
    }

    private DataListener<SocketEventData> onViewerLeaved() {
        return (senderClient, data, ackSender) -> {
            log.info(data.toString());
            String room = data.getRoom();
            senderClient.leaveRoom(room);
            socketService.removeViewer(SocketEventData.builder().eventName(SERVER_EVENT_NAME).room(room).senderClient(senderClient).userKey(data.getUserKey()).build());
        };
    }
//    private

    private ConnectListener onConnected() {
        return client -> {
            log.info("Socket sessionId[{}] onConnected", client.getSessionId().toString());
        };
    }
    private DisconnectListener onDisconnected() {
        return client -> log.info("Socket sessionId[{}] onDisconnected", client.getSessionId().toString());

    }

    private <T> void addNamespaceEventListener(String namespace, String eventNm, Class<T> responseType, DataListener<T> eventListener) {
        SocketIONamespace socketIONamespace = server.addNamespace(namespace);
        socketIONamespace.addEventListener(eventNm, responseType, eventListener);
    }
}
