package com.arogyavarta.console.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.arogyavarta.console.dto.ChatMessageDTO;
import com.arogyavarta.console.dto.ChatResponseDTO;
import com.arogyavarta.console.entity.Chat;
import com.arogyavarta.console.service.ChatService;
import com.arogyavarta.console.utils.ObjectEncryptionUtility;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageDTO chatMessage) throws Exception{
        chatService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId().toString(), "/queue/messages",
                ChatResponseDTO.builder().senderId(chatMessage.getSenderId())
                .recipientId(chatMessage.getRecipientId())
                .consultationId(chatMessage.getConsultationId())
                .content(chatMessage.getContent()).build()
        );
    }

    @GetMapping("/messages/{consultationId}/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatResponseDTO>> findChatMessages(@PathVariable Long consultationId, 
                                                    @PathVariable Long senderId, 
                                                    @PathVariable Long recipientId){
        List<Chat> chats = chatService.findChatMessages(consultationId, senderId, recipientId);
        return ResponseEntity.ok(chats.stream().map(this::convertToChatResponseDTO).collect(Collectors.toList()));
    }


    private ChatResponseDTO convertToChatResponseDTO(Chat chat){
        ChatResponseDTO chatResponseDTO =  ChatResponseDTO.builder()
                .chatId(chat.getChatId())
                .consultationId(chat.getConsultation().getConsultationId())
                .senderId(chat.getSender().getUserId())
                .recipientId(chat.getRecipient().getUserId())
                .content(chat.getContent())
                .timestamp(chat.getTimestamp())
                .build();
        try {
            ObjectEncryptionUtility.decryptStringFields(chatResponseDTO);
        } catch (Exception e) {
            return null; 
        }
        return chatResponseDTO;
    }
}
