package com.example.etutorbackend.repository;

import com.example.etutorbackend.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(@Param("username") String username);

    @Query(value = "SELECT user.id, user.address, user.city, user.created_at, user.email, user.enabled, user.first_name,\n" +
            "user.last_name, user.last_updated, user.password, user.phone_number, user.profile_image_path, user.username " +
            "FROM user, message WHERE user.id = message.sender_id AND user.id = message.receiver_id AND sender_id = :userId OR receiver_id = :userId AND user.id != :userId", nativeQuery = true)
    Page<User> findUsersForConversationByUserId(@Param("userId") Long userId, Pageable pageable);
}
