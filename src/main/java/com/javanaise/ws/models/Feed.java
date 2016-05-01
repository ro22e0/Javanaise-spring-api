package com.javanaise.ws.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String author;

    @Column(nullable = false)
    private String link;

    @JsonIgnore
    @Column
    private String sourceLink;

    @Column
    private String uri;

    @Column
    private String imageUrl;

    @Column
    private String language;

    @Column
    private String copyright;

    @Column
    private Date pubDate;

    @Column
    private Date lastBuildDate;

    @Column
    private String docs;

    @Column
    private String managingEditor;

    @Column
    private String webMaster;

    @Column
    private String category;

    @Column
    private String generator;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "feed", cascade = CascadeType.ALL)
    private Set<UserFeed> userFeeds = new HashSet<UserFeed>();

    @JsonIgnore
    @OneToMany(mappedBy = "feed")
    private Set<Item> items = new HashSet<>();

    public Feed(String title, String link, String description, String language, String copyright, Date pubDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.language = language;
        this.copyright = copyright;
        this.pubDate = pubDate;
    }

    Feed() { // jpa only
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLanguage() {
        return language;
    }

    public String getCopyright() {
        return copyright;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public Date getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(Date lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public String getDocs() {
        return docs;
    }

    public void setDocs(String docs) {
        this.docs = docs;
    }

    public String getManagingEditor() {
        return managingEditor;
    }

    public void setManagingEditor(String managingEditor) {
        this.managingEditor = managingEditor;
    }

    public String getWebMaster() {
        return webMaster;
    }

    public void setWebMaster(String webMaster) {
        this.webMaster = webMaster;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public Set<UserFeed> getUserFeeds() {
        return userFeeds;
    }

    public void addUserFeed(UserFeed userFeed) {
        this.userFeeds.add(userFeed);
    }

    public void removeUserFeed(UserFeed userFeed) {
        this.getUserFeeds().remove(userFeed);
    }


    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }
}