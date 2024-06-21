package org.example.socketproject.socket;

import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.socketproject.constants.SocketEventName;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SocketConnectionListeners {
    private final SocketService socketService;

    protected ConnectListener onConnected() {
        return client ->
            log.info("Socket sessionId[{}] onConnected", client.getSessionId().toString());
    }

    protected DisconnectListener onDisconnected() {
        return client -> {
            log.info("Socket sessionId[{}] disconnected", client.getSessionId().toString());
            socketService.removeViewer(SocketEventName.VIEWER_COUNT, client);
        };
    }
}
