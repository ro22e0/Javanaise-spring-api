package com.javanaise.ws.models;

import javax.persistence.*;

@Entity
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column
    private String title;

    @Column(nullable = false)
    private String link;

    @Column
    private String description;

    @Column
    private String language;

    @Column
    private String copyright;

    @Column
    private String docs;

    @Column
    private String category;

    @Column
    private String managingEditor;

    @Column
    private String webMaster;

//    @JsonIgnore
//    @OneToMany(mappedBy = "article")
//    private Set<Article> items = new HashSet<>();

    public Feed(String link) {
        this.setLink(link);
    }

    Feed() { // jpa only
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getDocs() {
        return docs;
    }

    public void setDocs(String docs) {
        this.docs = docs;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

//    public Set<Article> getItems() {
//        return items;
//    }
}