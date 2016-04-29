package com.javanaise.ws.controllers;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.Map;

/**
 * Created by ro22e0 on 29/04/2016.
 */

@RestController
@RequestMapping(value = "/feeds", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeedController {

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Map<String, String> params) {

        try {
            URL feedUrl = new URL(params.get("url"));

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));
        } catch (Exception ex) {
            ResponseEntity<String> errorResponseEntity =
                    new ResponseEntity<String>("{\"error\": " + ex.getMessage() + "}", HttpStatus.BAD_REQUEST);
            return errorResponseEntity;
        }

        ResponseEntity<String> errorResponseEntity =
                new ResponseEntity<String>("{\"error\": \"Not found\"}", HttpStatus.NOT_FOUND);
        return errorResponseEntity;
    }
}
