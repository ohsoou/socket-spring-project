package org.example.socketproject.constants;

public class SocketStoreKey {
    private SocketStoreKey(){
        throw new IllegalStateException("SocketStoreKey class");
    }
    public static final String USER_KEY = "userKey";
    public static final String NAMESPACE = "namespace";
    public static final String ROOM = "room";
}
