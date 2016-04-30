package com.javanaise.ws.repositories;

import com.javanaise.ws.models.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by ro22e0 on 29/04/2016.
 */

public interface FeedRepository extends JpaRepository<Feed, Long> {
    Optional<Feed> findByLink(String link);

    Optional<Feed> findById(Long id);
}
