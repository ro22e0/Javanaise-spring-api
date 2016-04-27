package com.javanaise.ws.controllers;

import com.javanaise.ws.models.User;
import com.javanaise.ws.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by ro22e0 on 26/04/2016.
 */

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private UserRepository userRepository;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public User sayHello() {
        User user = new User("Ronaël", "Bajazet", "ronael.bajazet@epitech.eu", "blablabla");

        // this.userRepository.save(new User("Ronaël", "Bajazet", "ronael.bajazet@epitech.eu", "blablabla"));
        return user;
    }

//    @RequestMapping(value = "/sign_up", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long userId) {
        this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return this.userRepository.findOne(userId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> findAll() {
        final List<User> resultList = new ArrayList<>();
        final Iterable<User> all = this.userRepository.findAll();

        all.forEach(new Consumer<User>() {
            @Override
            public void accept(User user) {
                resultList.add(user);
            }
        });
        return resultList;
    }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("could not find user '" + userId + "'.");
    }
}