package org.example.socketproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.socketproject.model.SocketData;
import org.example.socketproject.repository.MonitoringRedisRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

//@Service
@Slf4j
@RequiredArgsConstructor
public class RedisMonitoringService implements MonitoringService {
    private final MonitoringRedisRepository redisRepository;

    public int getRealViewers(SocketData socketData) {
        Iterable<SocketData> findData = redisRepository.findAllByNamespace(socketData.getNamespace());
        Set<String> userKeys = new HashSet<>();

        findData.forEach(data -> userKeys.add(data.getUserKey()));

        return userKeys.size();
    }


    public void saveViewerData(SocketData socketData) {
        redisRepository.save(socketData);
    }

    public void deleteViewerData(SocketData socketData) {
        redisRepository.deleteById(socketData.getSocketId());
    }

    public void initRedisViewer() {
        redisRepository.deleteAll();
    }
}
