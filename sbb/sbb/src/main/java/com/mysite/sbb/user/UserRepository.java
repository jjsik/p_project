package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findByusername(String username);
    Optional<SiteUser> findByName(String name); // 이름으로 사용자 검색
    Optional<SiteUser> findByusernameAndName(String username, String name); // 아이디와 이름으로 사용자 검색
}