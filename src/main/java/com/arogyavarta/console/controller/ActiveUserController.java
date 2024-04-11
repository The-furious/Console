package com.arogyavarta.console.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arogyavarta.console.dto.ActiveUserDTO;
import com.arogyavarta.console.service.ActiveUserService;

@RestController
public class ActiveUserController {

    @Autowired
    private ActiveUserService activeUserService;

    @MessageMapping("/topic/addUser")
    @SendTo("/topic/activeUser")
    public ActiveUserDTO addUser(
            @Payload ActiveUserDTO user
    ) {
        activeUserService.saveUser(user);
        return user;
    }

    @MessageMapping("/topic/disconnectUser")
    @SendTo("/topic/activeUser")
    public ActiveUserDTO disconnectUser(
            @Payload ActiveUserDTO user
    ) {
        activeUserService.disconnect(user);
        return user;
    }

    @GetMapping("/activeUsers")
    public ResponseEntity<List<Long>> findConnectedUsers() {
        return ResponseEntity.ok(activeUserService.findConnectedUsers());
    }
}
