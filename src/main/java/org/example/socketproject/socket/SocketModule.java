package org.example.socketproject.socket;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.example.socketproject.model.SocketEventData;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketModule {
    public SocketModule(SocketIOServer server, SocketService socketService) {
        socketService.initSocket(server);
        SocketConnectionListeners connectionListeners = new SocketConnectionListeners(socketService);
        SocketEventListeners eventListeners = new SocketEventListeners(socketService);

        SocketIONamespace roomNamespace = server.addNamespace("/room");
        roomNamespace.addConnectListener(connectionListeners.onConnected());
        roomNamespace.addDisconnectListener(connectionListeners.onDisconnected());


        // namespace event
        roomNamespace.addEventListener("addViewer", SocketEventData.class, eventListeners.onViewerReceived());
        roomNamespace.addEventListener("leaveViewer", SocketEventData.class, eventListeners.onViewerLeaved());
    }

}
