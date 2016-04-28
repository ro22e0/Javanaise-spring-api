package com.javanaise.ws.models;

import javax.persistence.*;

/**
 * Created by corgi on 28/04/16.
 */

@Entity
public class Rss {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String description;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String link;

    public Long getId(){
        return id;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setLink(String link){
        this.link = link;
    }

    public String getLink(){
        return this.link;
    }

    public String getXML(){
        return "No XML";
    }
}
