package com.arogyavarta.console.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatResponseDTO {
    private Long chatId;
    private Long consultationId;
    private Long senderId;
    private Long recipientId;
    private String content;
    private LocalDateTime timestamp;
}
