package com.javanaise.ws.controllers;

import com.javanaise.ws.models.User;
import com.javanaise.ws.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by ro22e0 on 26/04/2016.
 */

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/sign_up", method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@RequestBody Map<String, String> params) {
        Optional<User> oUser = this.userRepository.findByEmail(params.get("email"));
        if (!oUser.isPresent()) {
            User user = new User(params.get("email"), params.get("password"));
            return new ResponseEntity<User>(userRepository.save(user), HttpStatus.CREATED);
        }

        ResponseEntity<String> errorResponseEntity =
                new ResponseEntity<String>("{\"error\": \"user already exist\"}", HttpStatus.BAD_REQUEST);

        return errorResponseEntity;
    }

    @RequestMapping(value = "/sign_in", method = RequestMethod.POST)
    public ResponseEntity<?> signIn(@RequestBody Map<String, String> params) {
        ResponseEntity<String> errorResponseEntity =
                new ResponseEntity<String>("{\"status\": \"ahahah t'as cru que c'était possible\"}", HttpStatus.BAD_REQUEST);

        return errorResponseEntity;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Map<String, String> params) {
        Optional<User> user = this.userRepository.findById(Long.getLong(params.get("id")));
        if (!user.isPresent()) {
            ResponseEntity<String> errorResponseEntity =
                    new ResponseEntity<String>("{\"error\": \"could not find user\"}", HttpStatus.NOT_FOUND);
            return errorResponseEntity;
        }
        if (params.get("email") != "") {
            user.get().setEmail(params.get("email"));
        }
        if (params.get("password") != "") {
            user.get().setEmail(params.get("password"));
        }
        if (params.get("firstname") != "") {
            user.get().setEmail(params.get("firstname"));
        }
        if (params.get("lastname") != "") {
            user.get().setEmail(params.get("lastname"));
        }
        return new ResponseEntity<User>(userRepository.save(user.get()), HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        Optional<User> user = this.userRepository.findById(userId);
        if (!user.isPresent()) {
            ResponseEntity<String> errorResponseEntity =
                    new ResponseEntity<String>("{\"error\": \"could not find user\"}", HttpStatus.NOT_FOUND);

            return errorResponseEntity;
        }
        return new ResponseEntity<User>(this.userRepository.findOne(userId), HttpStatus.FOUND);
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