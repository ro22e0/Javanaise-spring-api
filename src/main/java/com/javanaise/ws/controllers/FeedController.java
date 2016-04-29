package com.javanaise.ws.controllers;

import com.javanaise.ws.models.Feed;
import com.javanaise.ws.repositories.FeedRepository;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by ro22e0 on 29/04/2016.
 */

@RestController
@RequestMapping(value = "/feeds", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeedController {

    @Autowired
    private FeedRepository feedRepository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Map<String, String> params) {

        URL feedUrl = null;
        try {
            feedUrl = new URL(params.get("url"));
        } catch (MalformedURLException e) {
            ResponseEntity<String> errorResponseEntity =
                    new ResponseEntity<String>("{\"error\": " + e.getMessage() + "}", HttpStatus.BAD_REQUEST);
            return errorResponseEntity;
        }

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = null;
        try {
            feed = input.build(new XmlReader(feedUrl));
        } catch (FeedException e) {
            ResponseEntity<String> errorResponseEntity =
                    new ResponseEntity<String>("{\"error\": " + e.getMessage() + "}", HttpStatus.BAD_REQUEST);
            return errorResponseEntity;
        } catch (IOException e) {
            ResponseEntity<String> errorResponseEntity =
                    new ResponseEntity<String>("{\"error\": " + e.getMessage() + "}", HttpStatus.BAD_REQUEST);
            return errorResponseEntity;
        }

        Feed myFeed = new Feed(feed.getLink());
        myFeed.setCopyright(feed.getCopyright());
        myFeed.setDescription(feed.getDescription());
        myFeed.setDocs(feed.getDocs());
        myFeed.setLanguage(feed.getLanguage());
        myFeed.setManagingEditor(feed.getManagingEditor());
        myFeed.setTitle(feed.getTitle());
        //myFeed.setCategory(feed.getCategories().get(0).getName());

        return new ResponseEntity<Feed>(feedRepository.save(myFeed), HttpStatus.CREATED);
    }
}
