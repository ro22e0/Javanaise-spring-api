package com.javanaise.ws.repositories;

import com.javanaise.ws.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by ro22e0 on 26/04/2016.
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
    Optional<User> findById(Long id);
}