package com.mysite.sbb.user.repository;

import java.util.Optional;

import com.mysite.sbb.user.DTO.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    // 아이디로 찾기
    Optional<SiteUser> findByusername(String username);
    Optional<SiteUser> findByName(String name);
    Optional<SiteUser> findByusernameAndName(String username, String name);
}