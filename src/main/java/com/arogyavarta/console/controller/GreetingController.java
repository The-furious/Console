package com.arogyavarta.console.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/greetings")
public class GreetingController {

    @Autowired
    private SimpMessagingTemplate template;


    @GetMapping("greet")
    public void getMethodName(@RequestParam String greeting) {
        String text = greeting;
        this.template.convertAndSend("/topic/greetings", text);
    }

}