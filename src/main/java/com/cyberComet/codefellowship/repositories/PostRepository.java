package com.cyberComet.codefellowship.repositories;

import com.cyberComet.codefellowship.models.Post;
import com.cyberComet.codefellowship.models.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    public Post findByBody(String body);
}
