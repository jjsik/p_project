package com.mysite.sbb.user.service;

import java.util.Optional;

import com.mysite.sbb.user.DTO.AIFashionDTO;
import com.mysite.sbb.user.DTO.ImageResDTO;
import com.mysite.sbb.user.DTO.SiteUser;
import com.mysite.sbb.user.config.ImageConfig;
import com.mysite.sbb.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final RestTemplate restTemplate;

    @Value("${apiKey.key}")
    private String apiKey;

    public ImageResDTO FashionType(AIFashionDTO dto){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(ImageConfig.MEDIA_TYPE));
        httpHeaders.add(ImageConfig.AUTHORIZATION, ImageConfig.BEARER + apiKey);

        HttpEntity<AIFashionDTO> resquestHttpEntity = new HttpEntity<>(dto, httpHeaders);
        ResponseEntity<ImageResDTO> responseEntity = restTemplate.postForEntity(ImageConfig.IMAGE_URL, resquestHttpEntity, ImageResDTO.class);

        return responseEntity.getBody();
    }

    public SiteUser create(String username, String name, String nickname, String password, String gender) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setName(name);
        user.setNickname(nickname);
        user.setPassword(passwordEncoder.encode(password));
        user.setGender(gender);
        this.userRepository.save(user);
        return user;
    }

    public String findUsername(String name) {
        Optional<SiteUser> siteUser = this.userRepository.findByName(name);
        return siteUser.map(SiteUser::getUsername).orElse(null);
    }

//    public boolean findPassword(String username, String name) {
//        Optional<SiteUser> siteUser = this.userRepository.findByusernameAndName(username, name);
//        if (siteUser.isPresent()) {
//            // TODO: 임시 비밀번호 생성 및 이메일 발송 로직 추가
//            return true;
//        }
//        return false;
//    }

    public int login(String username, String password) {
        Optional<SiteUser> user = this.userRepository.findByusername(username);

        if(user.isPresent())
        {
            if(passwordEncoder.matches(password, user.map(SiteUser::getPassword).orElse(null)))
                //아이디와 비밀번호가 일치할 경우
                return 1;
            else
                // 아이디는 존재하나 비밀번호가 틀릴경우
                return 2;
        }
            // 아이디가 존재하지 않을 경우
            return 0;
   }
}