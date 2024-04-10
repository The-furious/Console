package com.arogyavarta.console.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByConsultation_ConsultationIdAndSender_UserIdAndRecipient_UserIdOrderByTimestampAsc(
            Long consultationId, Long senderId, Long recipientId);
}
