package com.javanaise.ws.repositories;

import com.javanaise.ws.models.Rss;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by corgi on 28/04/16.
 */

public interface RssRepository extends JpaRepository<Rss, Long> {
    Rss findByLink(String link);
    Rss findByName(String name);
}
