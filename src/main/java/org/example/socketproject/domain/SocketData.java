package org.example.socketproject.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Getter
@RedisHash(value = "visitor")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class SocketData {
    @Id
    private String id;
    private List<String> userKeys;
}
