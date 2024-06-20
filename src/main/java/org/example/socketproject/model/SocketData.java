package org.example.socketproject.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Set;

@Getter
@RedisHash(value = "socket")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class SocketData {
    @Id
    private String id;
//    private String namespace;
//    private String room;
//
//    private String userKey;
    private Set<String> userKeys;
}
