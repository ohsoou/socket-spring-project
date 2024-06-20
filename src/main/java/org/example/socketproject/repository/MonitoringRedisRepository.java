package org.example.socketproject.repository;

import org.example.socketproject.model.SocketData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoringRedisRepository extends CrudRepository<SocketData, String> {
}
