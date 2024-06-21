package org.example.socketproject.constants;


public class SocketMessageType {
    private SocketMessageType() {
        throw new IllegalStateException("SocketMessageType class");
    }
    public static final String SERVER = "SERVER_MESSAGE";
    public static final String CLIENT = "CLIENT_MESSAGE";
}
