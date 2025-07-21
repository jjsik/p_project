package pproject.stylelobo.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyStyleFashionDetailDto {


    private String nickname;
    private String selectedStyles;
    private byte[] ImageBytes;
    private String diagnosedStyle;
    private LocalDateTime createAt;
    private String selectedFace;
    private String selectedBody;
    private String Base64Image;

    public MyStyleFashionDetailDto(String nickname,
                                   String selectedStyles, byte[] ImageBytes, LocalDateTime createAt, String selectedFace, String selectedBody) {

        this.nickname = nickname;
        this.selectedStyles = selectedStyles;
        this.ImageBytes = ImageBytes;
        this.createAt = createAt;
        this.selectedFace = selectedFace;
        this.selectedBody = selectedBody;
    }
}
