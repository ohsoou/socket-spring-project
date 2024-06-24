package org.example.socketproject;

import org.example.socketproject.model.SocketData;
import org.example.socketproject.repository.MonitoringRedisRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class RedisTest {

    @Autowired
    private MonitoringRedisRepository monitoringRedisRepository;

    @Test
    void saveTest() {
        //given
        SocketData socketData = SocketData.builder().socketId("socketId").userKey("userKey").namespace("namespace").build();
        SocketData socketData2 = SocketData.builder().socketId("socketId2").userKey("userKey").namespace("namespace").build();

        //when
        monitoringRedisRepository.save(socketData);
        monitoringRedisRepository.save(socketData2);

        //then
        Optional<SocketData> findSocket = monitoringRedisRepository.findById(socketData.getSocketId());
        Iterable<SocketData> findSockets = monitoringRedisRepository.findAllByNamespace(socketData.getNamespace());
        assertThat(findSocket.isPresent()).isEqualTo(Boolean.TRUE);
        findSockets.forEach((data)-> assertThat(socketData.getUserKey()).isEqualTo(data.getUserKey()));
    }
}
