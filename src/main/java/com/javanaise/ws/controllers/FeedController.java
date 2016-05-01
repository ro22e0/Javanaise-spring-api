package com.javanaise.ws.controllers;

import com.javanaise.ws.models.Feed;
import com.javanaise.ws.models.Item;
import com.javanaise.ws.models.User;
import com.javanaise.ws.models.UserFeed;
import com.javanaise.ws.repositories.FeedRepository;
import com.javanaise.ws.repositories.ItemRepository;
import com.javanaise.ws.repositories.UserFeedRepository;
import com.javanaise.ws.repositories.UserRepository;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;

@RestController
@RequestMapping(value = "/feeds", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class FeedController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserFeedRepository userFeedRepository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> create(HttpServletRequest request, @RequestBody Map<String, String> params) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("weird");
            System.out.println(session.getId());
            return new ResponseEntity<String>("{\"error\": \"Not connected.\"}", HttpStatus.UNAUTHORIZED);
        }

        User user = (User) session.getAttribute("user");

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

        UserFeed userFeed = new UserFeed();
        userFeed.setUser(user);

        Optional<Feed> oFeed = feedRepository.findByLink(feed.getLink());
        if (oFeed.isPresent()) {
            userFeed.setFeed(oFeed.get());

            userRepository.save(user);
            userFeedRepository.save(userFeed);
            return new ResponseEntity<Feed>(feedRepository.save(oFeed.get()), HttpStatus.CREATED);
        }

        Feed myFeed = new Feed(feed.getTitle(), feed.getLink(), feed.getDescription(), feed.getLanguage(), feed.getCopyright(), feed.getPublishedDate());
        myFeed.setDocs(feed.getDocs());
        myFeed.setSourceLink(params.get("url"));
        myFeed.setManagingEditor(feed.getManagingEditor());
        myFeed.setGenerator(feed.getGenerator());
        if (feed.getImage() != null)
            myFeed.setImageUrl(feed.getImage().getUrl());
        myFeed.setUri(feed.getUri());
        myFeed.setAuthor(feed.getAuthor());
        if (!feed.getCategories().isEmpty())
            myFeed.setCategory(feed.getCategories().get(0).getName());
        myFeed = this.feedRepository.save(myFeed);

        for (Iterator<SyndEntry> i = feed.getEntries().iterator(); i.hasNext(); ) {
            SyndEntry entry = i.next();

            Optional<Item> oItem = itemRepository.findByLink(entry.getLink());
            if (!oItem.isPresent()) {
                Item item = new Item(myFeed, entry.getLink());
                item.setUri(entry.getUri());
                if (entry.getDescription() != null)
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
        }
        userFeed.setFeed(myFeed);
        userRepository.save(user);
        userFeedRepository.save(userFeed);
        return new ResponseEntity<Feed>(feedRepository.save(myFeed), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{feedId}", method = RequestMethod.GET)
    public ResponseEntity<?> getFeed(HttpServletRequest request, @PathVariable Long feedId) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return new ResponseEntity<String>("{\"error\": \"Not connected.\"}", HttpStatus.UNAUTHORIZED);

        Optional<Feed> feed = this.feedRepository.findById(feedId);
        if (!feed.isPresent()) {
            ResponseEntity<String> errorResponseEntity =
                    new ResponseEntity<String>("{\"error\": \"could not find feed\"}", HttpStatus.NOT_FOUND);

            return errorResponseEntity;
        }
        return new ResponseEntity<Feed>(feed.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{feedId}/items", method = RequestMethod.GET)
    public ResponseEntity<?> getItems(HttpServletRequest request, @PathVariable Long feedId) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return new ResponseEntity<String>("{\"error\": \"Not connected.\"}", HttpStatus.UNAUTHORIZED);

        Optional<Feed> feed = this.feedRepository.findById(feedId);
        if (!feed.isPresent()) {
            ResponseEntity<String> errorResponseEntity =
                    new ResponseEntity<String>("{\"error\": \"could not find feed\"}", HttpStatus.NOT_FOUND);

            return errorResponseEntity;
        }
        HashMap<String, Collection<Item>> response = new HashMap<>();
        response.put("items", this.itemRepository.findByFeedId(feedId));
        return new ResponseEntity<HashMap<String, Collection<Item>>>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return new ResponseEntity<String>("{\"error\": \"Not connected.\"}", HttpStatus.UNAUTHORIZED);

        final List<Feed> resultList = new ArrayList<>();
        final Iterable<Feed> all = this.feedRepository.findAll();

        all.forEach(new Consumer<Feed>() {
            @Override
            public void accept(Feed feed) {
                resultList.add(feed);
            }
        });
        HashMap<String, List<Feed>> response = new HashMap<>();
        response.put("feeds", resultList);
        return new ResponseEntity<HashMap<String, List<Feed>>>(response, HttpStatus.OK);
    }

    @PostConstruct
    private void setFeeds() {
        addFeed("http://rss.lefigaro.fr/lefigaro/laune");
        addFeed("http://www.jeuxvideo.com/rss/rss.xml");
        addFeed("http://www.20minutes.fr/rss/france.xml");
        addFeed("http://www.lemonde.fr/rss/une.xml");
        addFeed("http://www.rue89.com/homepage/feed");
        addFeed("http://www.monde-diplomatique.fr/recents.xml");
        addFeed("http://www.clubic.com/xml/news.xml");
        addFeed("http://www.journaldugeek.com/rss.php");
        addFeed("http://rss.macg.co/");
        addFeed("http://www.pcinpact.com/include/news.xml");
        addFeed("http://www.ubergizmo.com/fr/index.xml");
        addFeed("http://www.presse-citron.net/feed/");
        addFeed("https://www.apple.com/main/rss/hotnews/hotnews.rss");
        addFeed("http://rss.cnn.com/rss/edition.rss");
    }

    @Async
    synchronized private void addFeed(String url) {
        URL feedUrl = null;
        try {
            feedUrl = new URL(url);
        } catch (MalformedURLException e) {
            return;
        }

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = null;
        try {
            feed = input.build(new XmlReader(feedUrl));
        } catch (FeedException e) {
            return;
        } catch (IOException e) {
            return;
        }

        Optional<Feed> oFeed = feedRepository.findByLink(feed.getLink());
        if (oFeed.isPresent()) {
            return;
        }

        Feed myFeed = new Feed(feed.getTitle(), feed.getLink(), feed.getDescription(), feed.getLanguage(), feed.getCopyright(), feed.getPublishedDate());
        myFeed.setDocs(feed.getDocs());
        myFeed.setSourceLink(url);
        myFeed.setManagingEditor(feed.getManagingEditor());
        myFeed.setGenerator(feed.getGenerator());
        if (feed.getImage() != null)
            myFeed.setImageUrl(feed.getImage().getUrl());
        myFeed.setUri(feed.getUri());
        myFeed.setAuthor(feed.getAuthor());
        if (!feed.getCategories().isEmpty())
            myFeed.setCategory(feed.getCategories().get(0).getName());
        myFeed = feedRepository.save(myFeed);

        for (Iterator<SyndEntry> i = feed.getEntries().iterator(); i.hasNext(); ) {
            SyndEntry entry = i.next();

            Optional<Item> oItem = itemRepository.findByLink(entry.getLink());
            if (!oItem.isPresent()) {
                Item item = new Item(myFeed, entry.getLink());
                item.setUri(entry.getUri());
                if (entry.getDescription() != null)
                    item.setDescription(entry.getDescription().getValue());
                item.setPubDate(entry.getPublishedDate());
                item.setTitle(entry.getTitle());
                item.setAuthor(entry.getAuthor());
                item.setComments(entry.getComments());
                item.setSource(myFeed.getLink());
                if (!entry.getCategories().isEmpty())
                    item.setCategory(entry.getCategories().get(0).getName());
                itemRepository.save(item);
                myFeed.addItem(item);
            }
        }
        feedRepository.save(myFeed);
    }
}