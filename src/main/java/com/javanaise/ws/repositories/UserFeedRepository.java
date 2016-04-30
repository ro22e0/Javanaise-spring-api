package com.javanaise.ws.repositories;

import com.javanaise.ws.models.UserFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by ro22e0 on 30/04/2016.
 */

@Transactional(readOnly = true)
public interface UserFeedRepository extends JpaRepository<UserFeed, Long> {
    Collection<UserFeed> findByUserId(Long id);

    Optional<UserFeed> findByUserIdAndFeedId(Long userId, Long feedId);

    @Modifying
    @Transactional
    @Query("delete from UserFeed uf where uf.id = ?1")
    void deleteById(Long id);
}