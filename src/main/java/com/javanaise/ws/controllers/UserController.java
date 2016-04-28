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

    @RequestMapping("/hello")
    @ResponseBody
    public User get(String email){
        try {
            return userRepository.findByEmail(email);
        }
        catch (Exception e) {
            return null;
        }
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "webapi/users/create", method = RequestMethod.POST)
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
    
    @RequestMapping(value = "webapi/users", method = RequestMethod.GET)
    @ResponseBody
    public String getUsers(){
        /*
            Method to get all the users. Need to convert it to JSON format
         */
        List<User> users = userRepository.findAll();
        String output = "";
        for (final User user: users) {
            output += user.getEmail() + ";";
        }
        return output;
    }

    @RequestMapping(value = "webapi/users/delete/{username}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@PathVariable String username){
        User user = userRepository.findByUsername(username);
        if (user != null){
            userRepository.delete(user.getId());
            return "Success: user deleted";
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