package org.example.socketproject.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.socketproject.constants.SocketEventName;
import org.example.socketproject.constants.SocketStoreKey;
import org.example.socketproject.model.SocketEventData;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SocketEventListeners {
    private final SocketService socketService;

    protected DataListener<SocketEventData> onViewerReceived() {
        return (senderClient, data, ackSender) -> {
            log.info(data.toString());
            socketService.addViewer(SocketEventName.VIEWER_COUNT, setSocketDataInStore(senderClient, data));
        };
    }

    protected DataListener<SocketEventData> onViewerLeaved() {
        return (senderClient, data, ackSender) -> {
            log.info(data.toString());
            socketService.removeViewer(SocketEventName.VIEWER_COUNT, senderClient);
        };
    }

    private SocketIOClient setSocketDataInStore(SocketIOClient client, SocketEventData data) {
        client.set(SocketStoreKey.USER_KEY, data.getUserKey());
        client.set(SocketStoreKey.ROOM, data.getRoom());
        client.set(SocketStoreKey.NAMESPACE, client.getNamespace());

        return client;
    }

}
