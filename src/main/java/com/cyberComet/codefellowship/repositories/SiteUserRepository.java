package com.cyberComet.codefellowship.repositories;

import com.cyberComet.codefellowship.models.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {

    public SiteUser findByUsername(String username);
}
