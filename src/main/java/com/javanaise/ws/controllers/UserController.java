package com.javanaise.ws.controllers;

import com.javanaise.ws.models.User;
import com.javanaise.ws.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by ro22e0 on 26/04/2016.
 */

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //@RequestMapping(value = "/hello", method = RequestMethod.GET)
    @RequestMapping("/hello")
    @ResponseBody
    public User get(String email){
        //User user = new User("Ronaël", "Bajazet", "ronael.bajazet@epitech.eu", "blablabla");
        try {
            User user = userRepository.findByEmail(email);
            return user;
        }
        catch (Exception e) {
            return null;
        }
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

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    @ResponseBody
    public String create(@RequestBody User user){
        try {
            if (userRepository.findByEmail(user.getEmail()) == null) {
                userRepository.save(user);
                return "Success : user created";
            }
            return "Failed : cannot create user";
        }
        catch (Exception e){
            return "Failed: some fields may be empty : mandatory field : email, password, username";
        }

    }

    @RequestMapping(value = "/users/delete/{username}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@PathVariable String username){
        User user = userRepository.findByUsername(username);
        if (user != null){
            userRepository.delete(user.getId());
            return "Sucess: user deleted";
        }
        return "Failed : " + username + " not found";
    }

}

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("could not find user '" + userId + "'.");
    }
}