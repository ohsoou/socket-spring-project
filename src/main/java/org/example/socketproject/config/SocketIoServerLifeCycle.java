package org.example.socketproject.config;

import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class SocketIoServerLifeCycle {
    private final SocketIOServer server;

    public SocketIoServerLifeCycle(SocketIOServer server) {
        this.server = server;
    }


//    @PostConstruct
//    public void start() {
//        server.start();
//    }


    @PreDestroy
    public void stop() {
        server.stop();
    }
}
