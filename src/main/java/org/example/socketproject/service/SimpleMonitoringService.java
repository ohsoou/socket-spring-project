package org.example.socketproject.service;

import io.netty.util.internal.PlatformDependent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.socketproject.model.SocketData;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentMap;


@Service
@Slf4j
@RequiredArgsConstructor
public class SimpleMonitoringService implements MonitoringService{
    private final ConcurrentMap<String, SocketData> sockets = PlatformDependent.newConcurrentHashMap();

    @Override
    public int getRealViewers(SocketData socketData) {
        long count = sockets.entrySet()
                .stream()
                .filter((set) -> set.getValue().existSocketInNamespace(socketData.getNamespace()))
                .count();
        return Integer.parseInt(String.valueOf(count));
    }

    @Override
    public void saveViewerData(SocketData socketData) {
        this.sockets.putIfAbsent(socketData.getSocketId(), socketData);
    }

    @Override
    public void deleteViewerData(SocketData socketData) {
        this.sockets.remove(socketData.getSocketId());
    }

    @Override
    public void initRedisViewer() {
        this.sockets.clear();
    }
}
