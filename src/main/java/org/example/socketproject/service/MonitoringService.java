package org.example.socketproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.socketproject.model.SocketData;
import org.example.socketproject.repository.MonitoringRedisRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MonitoringService {
    private final MonitoringRedisRepository redisRepository;

    public Integer getUserCount(String key) {
        return 0;
    }

    public Set<String> getViewers(String id) {
        SocketData redisData = redisRepository.findById(id).orElse(SocketData.builder().build());
        log.info("findData: {}", redisData.toString());

        return Optional.ofNullable(redisData.getUserKeys()).orElse(new HashSet<>());
    }


    public int saveViewerData(SocketData socketData) {
        redisRepository.save(socketData);
        Set<String> viewers = getViewers(socketData.getId());
        log.info("viewer count: {}", viewers.size());
        return viewers.size();
    }
}
