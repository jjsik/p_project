package pproject.stylelobo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pproject.stylelobo.config.ImageConfig;
import pproject.stylelobo.domain.dto.AiFashionDTO;
import pproject.stylelobo.domain.dto.FashionTypeDTO;
import pproject.stylelobo.domain.dto.ImageResDTO;
import pproject.stylelobo.domain.table.FavoriteFashionResults;
import pproject.stylelobo.domain.table.Users;
import pproject.stylelobo.repository.FavoriteFashionResultsRepository;

@RequiredArgsConstructor
@Service
public class FashionImageService {

    @Autowired
    private final RestTemplate restTemplate;

    private final FavoriteFashionResultsRepository favoriteFashionResultsRepository;

    @Value("${apiKey.key}")
    private String apiKey;

    public ImageResDTO FashionType(AiFashionDTO dto, FashionTypeDTO fas){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(ImageConfig.MEDIA_TYPE));
        httpHeaders.add(ImageConfig.AUTHORIZATION, ImageConfig.BEARER + apiKey);
        httpHeaders.add(ImageConfig.MODEL, ImageConfig.MODEL); // 모델 지정

        HttpEntity<AiFashionDTO> requestHttpEntity = new HttpEntity<>(dto, httpHeaders);
        ResponseEntity<ImageResDTO> responseEntity = restTemplate.postForEntity(
                ImageConfig.IMAGE_URL,
                requestHttpEntity,
                ImageResDTO.class
        );

        // 응답에서 URL 추출
        ImageResDTO response = responseEntity.getBody();

        response.setDto(fas);

        return response;
    }

    public FavoriteFashionResults saveFashionImage(String selectedStyles, String preferredStyleInput, byte[] diagnosedStyle, Users user,
                                                    String selectedFace, String selectedBody){

        FavoriteFashionResults personalColor = new FavoriteFashionResults(selectedStyles, preferredStyleInput, diagnosedStyle, selectedFace, selectedBody, user);

        return favoriteFashionResultsRepository.save(personalColor);
    }
}
