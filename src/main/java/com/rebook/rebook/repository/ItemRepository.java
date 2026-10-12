
package com.rebook.rebook.repository;

import com.rebook.rebook.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}

