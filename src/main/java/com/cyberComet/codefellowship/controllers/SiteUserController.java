package com.cyberComet.codefellowship.controllers;

import com.cyberComet.codefellowship.models.SiteUser;
import com.cyberComet.codefellowship.repositories.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpClient;
import java.security.Principal;
import java.util.Date;

@Controller
public class SiteUserController {

    @Autowired
    SiteUserRepository siteUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/")
    public String getHomePage(Model m, Principal p){
        if(p != null){
            String username = p.getName();
            SiteUser foundUser = siteUserRepository.findByUsername(username);

            m.addAttribute("username", username);
            m.addAttribute("bio", foundUser.getBio());
        }
        return "index";
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
    public RedirectView createUser(String username, String password, String firstname, String lastname, String bio){
        String hashedPW = passwordEncoder.encode(password);
        SiteUser newUser = new SiteUser(username, hashedPW, firstname, lastname, bio);
        siteUserRepository.save(newUser);
        // auto login -> httpServletRequest
        authWithHttpServletRequest(username, password);
        return new RedirectView("/");
    }

    @GetMapping("/sauce")
    public String getSauce(){
        return "secret";
    }
}
