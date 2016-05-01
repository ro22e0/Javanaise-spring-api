package com.javanaise.ws;

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
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Created by ro22e0 on 01/05/2016.
 */

@Component
@EnableAsync
public class FeedUpdaterTask {

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Scheduled(fixedRate = 1200000)
    public void reportCurrentTime() {
        final List<Feed> feeds = feedRepository.findAll();

        for (Feed feed : feeds) {
            updateFeed(feed);
        }
    }

    @Async
    synchronized private void updateFeed(Feed feed) {
        URL feedUrl = null;
        try {
            feedUrl = new URL(feed.getLink());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed sFeed = null;
        try {
            sFeed = input.build(new XmlReader(feedUrl));
        } catch (FeedException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        for (Iterator<SyndEntry> i = sFeed.getEntries().iterator(); i.hasNext(); ) {
            SyndEntry entry = i.next();

            Optional<Item> oItem = itemRepository.findByLink(entry.getLink());

            if (!oItem.isPresent()) {
                Item item = new Item(feed, entry.getLink());
                item.setUri(entry.getUri());
                if (entry.getDescription() != null)
                    item.setDescription(entry.getDescription().getValue());
                item.setPubDate(entry.getPublishedDate());
                item.setTitle(entry.getTitle());
                item.setAuthor(entry.getAuthor());
                item.setComments(entry.getComments());
                item.setSource(feed.getLink());
                if (!entry.getCategories().isEmpty())
                    item.setCategory(entry.getCategories().get(0).getName());
                this.itemRepository.save(item);
                feed.addItem(item);
            }
        }
        feedRepository.save(feed);
    }
}