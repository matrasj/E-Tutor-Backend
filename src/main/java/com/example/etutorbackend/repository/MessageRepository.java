package com.example.etutorbackend.repository;

import com.example.etutorbackend.model.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findBySenderIdOrderByCreatedAtDesc(@Param("userId") Long userId, Pageable pageable);
    Page<Message> findByReceiverIdOrderByCreatedAt(@Param("userId") Long userId, Pageable pageable);

    @Query(value = "SELECT * FROM message WHERE (message.receiver_id = :firstUserId AND message.sender_id = :secondUserId)" +
            "OR (message.sender_id = :firstUserId AND message.receiver_id = :secondUserId) ORDER BY created_at DESC\n", nativeQuery = true)
    List<Message> findMessagesForConversation(@Param("firstUserId") Long firstUserId, @Param("secondUserId") Long secondUserId);

}
