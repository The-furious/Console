package com.arogyavarta.console.dto;

import lombok.Data;

@Data
public class ChatMessageDTO {
    private Long senderId;
    private Long recipientId;
    private Long consultationId;
    private String content;
}
