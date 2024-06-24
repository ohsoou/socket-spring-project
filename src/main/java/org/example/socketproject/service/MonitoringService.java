package org.example.socketproject.service;

import org.example.socketproject.model.SocketData;

public interface MonitoringService {
    int getRealViewers(SocketData socketData);

    void saveViewerData(SocketData socketData);

    void deleteViewerData(SocketData socketData);

    void initRedisViewer();
}
