package com.javanaise.ws.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ro22e0 on 30/04/2016.
 */

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column
    private String title;

    @Column(nullable = false)
    private String link;

    @Column
    private String uri;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String source;

    @Column
    private String category;

    @Column
    private String comments;

    @Column
    private String author;

    @Column
    private Date pubDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private Feed feed;

    public Item(Feed feed, String link) {
        this.feed = feed;
        this.setLink(link);
    }

    Item() { // jpa only
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }
}
