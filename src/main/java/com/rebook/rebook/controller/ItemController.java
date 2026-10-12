
package com.rebook.rebook.controller;

import com.rebook.rebook.entity.Item;
import com.rebook.rebook.repository.ItemRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // Get all items
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return ResponseEntity.ok(items);
    }

    // Add an item for the logged-in student
    @PostMapping
    public ResponseEntity<?> addItem(
            @RequestBody Item item,
            HttpSession session) {

        Object sessionUserId = session.getAttribute("userId");

        if (!(sessionUserId instanceof Long)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "message",
                            "Please log in before adding an item."
                    ));
        }

        if (item.getTitle() == null
                || item.getTitle().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "message",
                            "Item title is required."
                    ));
        }

        try {
            item.setId(null);
            item.setOwnerId((Long) sessionUserId);

            Item savedItem = itemRepository.save(item);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedItem);

        } catch (Exception exception) {
            return ResponseEntity.status(
                            HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "message",
                            "Could not save the item. Please check the database and try again."
                    ));
        }
    }
}
