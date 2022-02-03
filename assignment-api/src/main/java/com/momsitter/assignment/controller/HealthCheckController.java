package com.momsitter.assignment.controller;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

//    헬스체킹 테스트를 위해 일시 주석
//    @GetMapping("/health")
//    public ResponseEntity<Void> healthChecking() {
//        return ResponseEntity.status(OK).build();
//    }
}
