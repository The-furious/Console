package com.arogyavarta.console.websocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {

    @MessageMapping("/user.addUser")
    @SendTo("/user/public")
    public String addUser(
            @Payload Input user
    ) {
        return user.getMessage();
    }

}
@Data
class Input {
    private String message;
    
}
