package com.cyberComet.codefellowship.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String body;
    private Date createdAt;

    @ManyToOne
    SiteUser mySiteUser;

    public Post() {
    }

    public Post(String body, Date createdAt, SiteUser mySiteUser) {
        this.body = body;
        this.createdAt = createdAt;
        this.mySiteUser = mySiteUser;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public SiteUser getMySiteUser() {
        return mySiteUser;
    }
}
