package com.javanaise.ws.controllers;

import com.javanaise.ws.models.Feed;
import com.javanaise.ws.models.User;
import com.javanaise.ws.models.UserFeed;
import com.javanaise.ws.repositories.FeedRepository;
import com.javanaise.ws.repositories.UserFeedRepository;
import com.javanaise.ws.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by ro22e0 on 30/04/2016.
 */

@RestController
@RequestMapping(value = "/feeds", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserFeedController {

    @Autowired
    UserFeedRepository userFeedRepository;

    @Autowired
    FeedRepository feedRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/subscribed", method = RequestMethod.GET)
    public ResponseEntity<?> subscribed(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ResponseEntity<String>("{\"error\": \"Not connected.\"}", HttpStatus.UNAUTHORIZED);
        }
        User user = (User) session.getAttribute("user");
        Collection<UserFeed> userFeeds = userFeedRepository.findByUserId(user.getId());

        final List<Feed> resultList = new ArrayList<>();
        userFeeds.forEach(new Consumer<UserFeed>() {
            @Override
            public void accept(UserFeed userFeed) {
                resultList.add(userFeed.getFeed());
            }
        });
        return new ResponseEntity<List<Feed>>(resultList, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public ResponseEntity<?> subscribe(HttpServletRequest request, @RequestBody Map<String, String> params) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return new ResponseEntity<String>("{\"error\": \"Not connected.\"}", HttpStatus.UNAUTHORIZED);

        Optional<Feed> feed = feedRepository.findById(Long.valueOf(params.get("id")));
        if (!feed.isPresent())
            return new ResponseEntity<String>("{\"error\": \"Feed not found.\"}", HttpStatus.NOT_FOUND);

        User user = (User) session.getAttribute("user");
        Optional<UserFeed> oUserFeed = userFeedRepository.findByUserIdAndFeedId(user.getId(), feed.get().getId());
        if (oUserFeed.isPresent())
            return new ResponseEntity<String>("{\"error\": \"Already subscribed\"}", HttpStatus.NOT_FOUND);

        UserFeed userFeed = new UserFeed();
        userFeed.setUser(user);
        userFeed.setFeed(feed.get());

        userRepository.save(user);
        userFeedRepository.save(userFeed);
        return new ResponseEntity<Feed>(feedRepository.save(feed.get()), HttpStatus.OK);
    }

    @RequestMapping(value = "/unsubscribe", method = RequestMethod.DELETE)
    public ResponseEntity<?> unsubscribe(HttpServletRequest request, @RequestBody Map<String, String> params) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return new ResponseEntity<String>("{\"error\": \"Not connected.\"}", HttpStatus.UNAUTHORIZED);

        Optional<Feed> feed = feedRepository.findById(Long.valueOf(params.get("id")));
        if (!feed.isPresent())
            return new ResponseEntity<String>("{\"error\": \"Feed not found.\"}", HttpStatus.NOT_FOUND);

        User user = (User) session.getAttribute("user");
        Optional<UserFeed> userFeed = userFeedRepository.findByUserIdAndFeedId(user.getId(), feed.get().getId());
        if (!userFeed.isPresent())
            return new ResponseEntity<String>("{\"error\": \"Not subscribed\"}", HttpStatus.NOT_FOUND);

        userFeedRepository.deleteById(userFeed.get().getId());
        userFeedRepository.flush();
        user.removeUserFeed(userFeed.get());
        feed.get().removeUserFeed(userFeed.get());
        userRepository.save(user);
        return new ResponseEntity<Feed>(feedRepository.save(feed.get()), HttpStatus.OK);
    }
}
