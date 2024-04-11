package com.arogyavarta.console.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.arogyavarta.console.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByConsultation_ConsultationIdAndSender_UserIdAndRecipient_UserIdOrderByTimestampAsc(
            Long consultationId, Long senderId, Long recipientId);
    
    @Query(value = "select * from chat where consultation_id=?1 and sender_id in (?2, ?3) and recipient_id in (?2, ?3) order by timestamp asc",
     nativeQuery = true)
    List<Chat> findChats(Long consultationId, Long senderId, Long recipientId);

    @Query(value = "select count(*) from chat where consultation_id=?1 and sender_id=?2 and recipient_id=?3 and received=0",
     nativeQuery = true)
    Long findUnreadMessages(Long consultationId, Long senderId, Long recipientId);
}
