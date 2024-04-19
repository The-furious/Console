package com.arogyavarta.console.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arogyavarta.console.dto.ChatMessageDTO;
import com.arogyavarta.console.entity.Chat;
import com.arogyavarta.console.entity.Consultation;
import com.arogyavarta.console.entity.User;
import com.arogyavarta.console.repo.ChatRepository;
import com.arogyavarta.console.repo.ConsultationRepository;
import com.arogyavarta.console.repo.UserRepository;
import com.arogyavarta.console.utils.ObjectEncryptionUtility;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    public void save(ChatMessageDTO chatMessageDTO) throws Exception{
        User sender = userRepository.findById(chatMessageDTO.getSenderId()).orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        User recipient = userRepository.findById(chatMessageDTO.getRecipientId()).orElseThrow(() -> new IllegalArgumentException("Recipient not found"));

        Consultation consultation = consultationRepository.findById(chatMessageDTO.getConsultationId()).orElseThrow(() -> new IllegalArgumentException("Consultation not found"));

        Chat chat = new Chat();
        chat.setSender(sender);
        chat.setRecipient(recipient);
        chat.setConsultation(consultation);
        chat.setContent(chatMessageDTO.getContent());
        chat.setReceived(false);
        chat.setTimestamp(LocalDateTime.now());
        ObjectEncryptionUtility.encryptStringFields(chat);
        chatRepository.save(chat);
    }


    public List<Chat> findChatMessages(Long consultationId, Long senderId, Long recipientId) {
        List<Chat> chatMessages = chatRepository.findChats(consultationId, senderId, recipientId);
        for (Chat chat : chatMessages) {
            chat.setReceived(true);
        }
        chatRepository.saveAll(chatMessages);

        return chatMessages; 
    }

    public Long findUnreadMessages(Long consultationId, Long senderId, Long recipientId)
    {
        return chatRepository.findUnreadMessages(consultationId, senderId, recipientId);
    }
    
}
