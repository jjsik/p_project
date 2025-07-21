package pproject.stylelobo.domain.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyStyleColorDetailDto {

    private String nickname;
    private String colorType;
    private byte[] faceImage;
    private String recommendation;
    private LocalDateTime createAt;


    public MyStyleColorDetailDto(String nickname,
                                 String colorType, byte[] faceImage, String recommendation, LocalDateTime createAt) {

        this.nickname = nickname;
        this.colorType = colorType;
        this.faceImage = faceImage;
        this.recommendation = recommendation;
        this.createAt = createAt;
    }
}
