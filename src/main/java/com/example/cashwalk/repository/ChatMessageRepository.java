package com.example.cashwalk.repository;

import com.example.cashwalk.entity.ChatMessage;
import com.example.cashwalk.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoomOrderByCreatedAtAsc(ChatRoom chatRoom);
    // ✅ 해당 채팅방에서 가장 마지막에 생성된 메시지 1개 조회
    Optional<ChatMessage> findTopByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom);

    @Modifying //DB데이터를 변경하는 쿼리
    @Transactional //트랙잭션보장(오류가나면 복구)
    @Query("UPDATE ChatMessage m SET m.isRead = true WHERE m.chatRoom = :room AND m.sender.id <> :userId AND m.isRead = false")
    int markMessagesAsReadByOpponent(@Param("room") ChatRoom room, @Param("userId") Long userId);
}
