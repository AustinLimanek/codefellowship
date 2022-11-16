package com.cyberComet.codefellowship.controllers;

import com.cyberComet.codefellowship.models.Post;
import com.cyberComet.codefellowship.models.SiteUser;
import com.cyberComet.codefellowship.repositories.PostRepository;
import com.cyberComet.codefellowship.repositories.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PostController {

    @Autowired
    SiteUserRepository siteUserRepository;
    @Autowired
    PostRepository postRepository;

    @PostMapping("/addPost")
    public RedirectView addPost(String body, String username){
        SiteUser siteuser = siteUserRepository.findByUsername(username);
        Date date = new Date();
        Post newPost = new Post(body, date, siteuser);
        postRepository.save(newPost);
        return new RedirectView("/");
    }

    @PostMapping("/deletePost")
    public RedirectView deletePost(String body){
        Post oldPost = postRepository.findByBody(body);
        postRepository.delete(oldPost);
        return new RedirectView("/");
    }
}
