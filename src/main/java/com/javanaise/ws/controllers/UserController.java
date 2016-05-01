package com.javanaise.ws.controllers;

import com.javanaise.ws.models.User;
import com.javanaise.ws.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/sign_up", method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@RequestBody Map<String, String> params) {
        Optional<User> oUser = this.userRepository.findByEmail(params.get("email"));
        if (!oUser.isPresent()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            User user = new User(params.get("email"), encoder.encode(params.get("password")));
            if (params.get("firstname") != "") {
                user.setFirstname(params.get("firstname"));
            }
            if (params.get("lastname") != "") {
                user.setLastname(params.get("lastname"));
            }
            return new ResponseEntity<User>(userRepository.save(user), HttpStatus.CREATED);
        }
        return new ResponseEntity<String>("{\"error\": \"user already exist\"}", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/sign_in", method = RequestMethod.POST)
    public ResponseEntity<?> signIn(HttpServletRequest request, @RequestBody Map<String, String> params, HttpSession session) {
        Optional<User> user = this.userRepository.findByEmail(params.get("email"));
        if (!user.isPresent()) {
            session.invalidate();
            return new ResponseEntity<String>("{\"error\": \"invalid email or password\"}", HttpStatus.NOT_FOUND);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(params.get("password"), user.get().getPassword())) {
            session.setAttribute("user", user.get());
            session.setMaxInactiveInterval(2629746);
            return new ResponseEntity<User>(user.get(), HttpStatus.OK);
        }
        session.invalidate();
        return new ResponseEntity<String>("{\"error\": \"invalid email or password\"}", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/sign_out", method = RequestMethod.DELETE)
    public ResponseEntity<?> signOut(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<String>("{\"status\": \"sucessful\"}", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody Map<String, String> params) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ResponseEntity<String>("{\"error\": \"Not connected.\"}", HttpStatus.UNAUTHORIZED);
        }
        User user = (User) session.getAttribute("user");
        if (params.get("email") != "") {
            user.setEmail(params.get("email"));
        }
        if (params.get("password") != "") {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(params.get("password")));
        }
        if (params.get("firstname") != "") {
            user.setFirstname(params.get("firstname"));
        }
        if (params.get("lastname") != "") {
            user.setLastname(params.get("lastname"));
        }
        return new ResponseEntity<User>(userRepository.save(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(HttpServletRequest request, @PathVariable Long userId) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ResponseEntity<String>("{\"error\": \"Not connected.\"}", HttpStatus.UNAUTHORIZED);
        }

        Optional<User> user = this.userRepository.findById(userId);
        if (!user.isPresent()) {
            return new ResponseEntity<String>("{\"error\": \"could not find user\"}", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user.get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ResponseEntity<String>("{\"error\": \"Not connected.\"}", HttpStatus.UNAUTHORIZED);
        }

        final List<User> resultList = new ArrayList<>();
        final Iterable<User> all = this.userRepository.findAll();

        all.forEach(new Consumer<User>() {
            @Override
            public void accept(User user) {
                resultList.add(user);
            }
        });
        return new ResponseEntity<List<User>>(resultList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(HttpServletRequest request, @PathVariable Long userId) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ResponseEntity<String>("{\"error\": \"Not connected.\"}", HttpStatus.UNAUTHORIZED);
        }

        User user = (User) session.getAttribute("user");
        if (user.getId() != userId) {
            return new ResponseEntity<String>("{\"error\": \"unauthorized\"}", HttpStatus.UNAUTHORIZED);
        }
        userRepository.deleteById(userId);
        userRepository.flush();
        session.invalidate();
        return new ResponseEntity<String>("{\"status\": \"complete\"}", HttpStatus.OK);
    }
}