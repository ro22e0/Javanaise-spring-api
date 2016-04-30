package com.javanaise.ws.repositories;

import com.javanaise.ws.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by ro22e0 on 26/04/2016.
 */

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);

    @Modifying
    @Transactional
    @Query("delete from User u where u.id = ?1")
    void deleteById(Long id);
}