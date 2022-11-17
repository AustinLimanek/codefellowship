package com.cyberComet.codefellowship.controllers;

import com.cyberComet.codefellowship.models.Post;
import com.cyberComet.codefellowship.models.SiteUser;
import com.cyberComet.codefellowship.repositories.PostRepository;
import com.cyberComet.codefellowship.repositories.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class SiteUserController {

    @Autowired
    SiteUserRepository siteUserRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/")
    public String getHomePage(Model m, Principal p){
        if(p != null){
            String username = p.getName();
            SiteUser foundUser = siteUserRepository.findByUsername(username);
            m.addAttribute("posts", foundUser.getPosts());
            m.addAttribute("username", username);
            m.addAttribute("bio", foundUser.getBio());
            m.addAttribute("users", siteUserRepository.findAll());
            Set<SiteUser> influencerSet = foundUser.getInfluencers();
            ArrayList<Post> feed = new ArrayList<>();
            for(SiteUser influ : influencerSet){
                feed.addAll(influ.getPosts());
            }
            m.addAttribute("feed", feed);
        }
        return "myprofile";
    }

    @GetMapping("login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/signup")
    public String getSignupPage(){
        return "signup";
    }

    public void authWithHttpServletRequest(String username, String password){
        try {
            request.login(username, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/signup")
    public RedirectView createUser(String username, String password, String firstname, String lastname, String bio, String datestring) throws ParseException {
        String hashedPW = passwordEncoder.encode(password);
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(datestring);
        SiteUser newUser = new SiteUser(username, hashedPW, firstname, lastname, bio, date);
        siteUserRepository.save(newUser);
        // auto login -> httpServletRequest
        authWithHttpServletRequest(username, password);
        return new RedirectView("/myprofile");
    }

    @PostMapping("/perceive/{friendname}")
    public String createPercep(Model m, Principal p, @PathVariable String friendname) {
        SiteUser friend = siteUserRepository.findByUsername(friendname);
        SiteUser siteUser = siteUserRepository.findByUsername(p.getName());
        siteUser.getInfluencers().add(friend);
        siteUserRepository.save(siteUser);
        m.addAttribute("username", siteUser.getUsername());
        m.addAttribute("friendname", friend.getUsername());
        m.addAttribute("posts", friend.getPosts());
        m.addAttribute("audience", friend.getAudience());
        m.addAttribute("influencers", friend.getInfluencers());

        return "friend";
    }

    @PostMapping("/friend/{friendname}")
    public String getUserInfo(Model m, Principal p, @PathVariable String friendname){
        SiteUser siteUser = siteUserRepository.findByUsername(p.getName());
        m.addAttribute("username", siteUser.getUsername());

        SiteUser friend = siteUserRepository.findByUsername(friendname);
        m.addAttribute("friendname", friend.getUsername());
        m.addAttribute("posts", friend.getPosts());
        m.addAttribute("audience", friend.getAudience());
        m.addAttribute("influencers", friend.getInfluencers());

        return "friend";
    }

    @PostMapping("/influencer/{id}")
    public String getUserInfo(Model m, Principal p, @PathVariable Long id){
        SiteUser siteUser = siteUserRepository.findByUsername(p.getName());
        m.addAttribute("username", siteUser.getUsername());

        SiteUser friend = siteUserRepository.findById(id).orElseThrow();
        m.addAttribute("friendname", friend.getUsername());
        m.addAttribute("posts", friend.getPosts());
        m.addAttribute("audience", friend.getAudience());
        m.addAttribute("influencers", friend.getInfluencers());

        return "friend";
    }

}
