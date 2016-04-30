package com.javanaise.ws.controllers;

import com.javanaise.ws.models.Feed;
import com.javanaise.ws.models.Item;
import com.javanaise.ws.repositories.FeedRepository;
import com.javanaise.ws.repositories.ItemRepository;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/feeds", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeedController {

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private ItemRepository itemRepository;

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

        System.out.println(feed.getEntries().get(0).getLink());

        Feed myFeed = new Feed(feed.getTitle(), feed.getLink(), feed.getDescription(), feed.getLanguage(), feed.getCopyright(), feed.getPublishedDate());
        myFeed.setDocs(feed.getDocs());
        myFeed.setManagingEditor(feed.getManagingEditor());
        myFeed.setGenerator(feed.getGenerator());
        myFeed.setImageUrl(feed.getImage().getUrl());
        myFeed.setUri(feed.getUri());
        myFeed.setAuthor(feed.getAuthor());
        if (!feed.getCategories().isEmpty())
            myFeed.setCategory(feed.getCategories().get(0).getName());
        myFeed = this.feedRepository.save(myFeed);

        for (Iterator<SyndEntry> i = feed.getEntries().iterator(); i.hasNext(); ) {
            SyndEntry entry = i.next();
            Item item = new Item(myFeed, entry.getLink());
            item.setUri(entry.getUri());
            item.setDescription(entry.getDescription().getValue());
            item.setPubDate(entry.getPublishedDate());
            item.setTitle(entry.getTitle());
            item.setAuthor(entry.getAuthor());
            item.setComments(entry.getComments());
            item.setSource(myFeed.getLink());
            if (!entry.getCategories().isEmpty())
                item.setCategory(entry.getCategories().get(0).getName());
            this.itemRepository.save(item);
            myFeed.addItem(item);
        }
        return new ResponseEntity<Feed>(feedRepository.save(myFeed), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{feedId}", method = RequestMethod.GET)
    public ResponseEntity<?> getFeed(@PathVariable Long feedId) {
        Optional<Feed> feed = this.feedRepository.findById(feedId);
        if (!feed.isPresent()) {
            ResponseEntity<String> errorResponseEntity =
                    new ResponseEntity<String>("{\"error\": \"could not find feed\"}", HttpStatus.NOT_FOUND);

            return errorResponseEntity;
        }
        return new ResponseEntity<Feed>(feed.get(), HttpStatus.FOUND);
    }

    @RequestMapping(value = "/{feedId}/items", method = RequestMethod.GET)
    public ResponseEntity<?> getItems(@PathVariable Long feedId) {
        Optional<Feed> feed = this.feedRepository.findById(feedId);
        if (!feed.isPresent()) {
            ResponseEntity<String> errorResponseEntity =
                    new ResponseEntity<String>("{\"error\": \"could not find feed\"}", HttpStatus.NOT_FOUND);

            return errorResponseEntity;
        }
        return new ResponseEntity<Collection<Item>>(this.itemRepository.findByFeedId(feedId), HttpStatus.FOUND);
    }
}
