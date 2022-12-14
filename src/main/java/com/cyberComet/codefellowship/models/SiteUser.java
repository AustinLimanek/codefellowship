package com.cyberComet.codefellowship.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
public class SiteUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String bio;
    private Date date;

    @OneToMany(mappedBy = "mySiteUser")
    List<Post> posts;

    public SiteUser() {
    }

    public SiteUser(String username, String password, String firstname, String lastname, String bio, Date date) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.bio = bio;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    @ManyToMany
    @JoinTable(
            name = "AudienceAndInfluencer",
            joinColumns = {@JoinColumn(name = "audience")},
            inverseJoinColumns = {@JoinColumn(name = "influencer")}
    )
    Set<SiteUser> influencers = new HashSet<>();

    @ManyToMany(mappedBy = "influencers")
    Set<SiteUser> audience = new HashSet<>();

    public Set<SiteUser> getInfluencers() {
        return influencers;
    }

    public Set<SiteUser> getAudience() {
        return audience;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getBio() {
        return bio;
    }

    public Date getDate() {
        return date;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
