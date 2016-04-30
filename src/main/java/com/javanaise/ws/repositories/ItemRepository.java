package com.javanaise.ws.repositories;

import com.javanaise.ws.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by ro22e0 on 30/04/2016.
 */

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByLink(String link);
    Collection<Item> findByFeedId(Long id);
}