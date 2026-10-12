
package com.rebook.rebook.controller;

import com.rebook.rebook.entity.Item;
import com.rebook.rebook.entity.Message;
import com.rebook.rebook.repository.ItemRepository;
import com.rebook.rebook.repository.MessageRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageRepository messageRepository;
    private final ItemRepository itemRepository;

    public MessageController(
            MessageRepository messageRepository,
            ItemRepository itemRepository) {
        this.messageRepository = messageRepository;
        this.itemRepository = itemRepository;
    }

    @PostMapping
    public ResponseEntity<?> sendMessage(
            @RequestBody Map<String, Object> request,
            HttpSession session) {

        Object sessionUserId = session.getAttribute("userId");

        if (!(sessionUserId instanceof Long)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Please log in to send a message."));
        }

        Long senderId = (Long) sessionUserId;

        try {
            Long itemId = Long.valueOf(request.get("itemId").toString());
            String content = String.valueOf(request.get("content")).trim();

            if (content.isBlank() || content.length() > 2000) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message",
                                "Message must contain 1–2000 characters."));
            }

            Item item = itemRepository.findById(itemId).orElse(null);

            if (item == null || item.getOwnerId() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Item or item owner not found."));
            }

            Long receiverId = item.getOwnerId();

            if (senderId.equals(receiverId)) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message",
                                "You cannot message yourself about your own item."));
            }

            Message message = new Message();
            message.setItemId(itemId);
            message.setSenderId(senderId);
            message.setReceiverId(receiverId);
            message.setContent(content);

            Message savedMessage = messageRepository.save(message);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);

        } catch (Exception exception) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message",
                            "Please provide a valid item ID and message."));
        }
    }

    @GetMapping
    public ResponseEntity<?> getConversation(
            @RequestParam Long itemId,
            @RequestParam Long otherUserId,
            HttpSession session) {

        Object sessionUserId = session.getAttribute("userId");

        if (!(sessionUserId instanceof Long)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Please log in to view messages."));
        }

        Long userId = (Long) sessionUserId;

        Item item = itemRepository.findById(itemId).orElse(null);

        if (item == null || item.getOwnerId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Item or item owner not found."));
        }

        Long ownerId = item.getOwnerId();

        // Only the owner and the student contacting the owner can view this chat.
        if (userId.equals(otherUserId)
                || (!userId.equals(ownerId) && !otherUserId.equals(ownerId))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message",
                            "You are not allowed to view this conversation."));
        }

        List<Message> messages = messageRepository.findConversation(
                itemId, userId, otherUserId);

        return ResponseEntity.ok(messages);
    }
}

