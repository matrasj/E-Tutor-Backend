package com.example.etutorbackend.repository;

import com.example.etutorbackend.model.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findBySenderIdOrderByCreatedAtDesc(@Param("userId") Long userId, Pageable pageable);
    Page<Message> findByReceiverIdOrderByCreatedAt(@Param("userId") Long userId, Pageable pageable);

}
