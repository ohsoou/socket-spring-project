package org.example.socketproject.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@Data
@Builder
@RedisHash(value = "visitor")
public class SocketData {
    @Id
    private String socketId;

    @Indexed
    private String namespace;

    private String userKey;

}
