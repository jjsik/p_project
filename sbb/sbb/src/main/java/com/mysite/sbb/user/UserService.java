package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String name, String nickname, String password, String gender) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setNickname(nickname);
        user.setGender(gender);
        this.userRepository.save(user);
        return user;
    }

    public String findUsernameByName(String name) {
        Optional<SiteUser> userOptional = userRepository.findByName(name);
        if (userOptional.isPresent()) {
            return userOptional.get().getUsername();
        } else {
            return null;
        }
    }

    public String findPasswordByNameAndUsername(String username, String name) {
        Optional<SiteUser> user = userRepository.findByusernameAndName(username, name);
        if (user.isPresent()) {
            return user.get().getPassword();
        } else {
            return null;
        }
    }
}