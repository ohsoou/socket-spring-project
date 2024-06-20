package org.example.socketproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.socketproject.service.MonitoringService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/monitoring")
public class MonitoringController {

    private final MonitoringService monitoringService;

    /**
     * 해당 페이지의 실시간 사용자 수 조회
     */
    @GetMapping("/{key}")
    public ResponseEntity<Integer> getUserCount(@PathVariable String key) {
        return ResponseEntity.ok(monitoringService.getUserCount(key));
    }



}
