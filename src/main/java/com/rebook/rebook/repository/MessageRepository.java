
package com.rebook.rebook.repository;

import com.rebook.rebook.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("""
        SELECT m FROM Message m
        WHERE m.itemId = :itemId
          AND (
              (m.senderId = :userId AND m.receiverId = :otherUserId)
              OR
              (m.senderId = :otherUserId AND m.receiverId = :userId)
          )
        ORDER BY m.sentAt ASC
        """)
    List<Message> findConversation(
            @Param("itemId") Long itemId,
            @Param("userId") Long userId,
            @Param("otherUserId") Long otherUserId
    );
}
