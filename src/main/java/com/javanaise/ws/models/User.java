package com.javanaise.ws.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by ro22e0 on 26/04/2016.
 */

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column(nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

//    @JsonIgnore
//    @OneToMany(mappedBy = "user")
//    private Set<UserFeed> userFeeds = new HashSet<UserFeed>();

    public User(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

    public User(String firstname, String lastname, String email, String password) {
        this.setFirstname(firstname);
        this.setLastname(lastname);
        this.setEmail(email);
        this.setPassword(password);
    }

    User() { // jpa only
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
