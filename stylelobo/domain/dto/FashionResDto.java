package pproject.stylelobo.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pproject.stylelobo.domain.Gender;

@Getter
@Setter
public class FashionResDto {
    private String imgUrl;
    private String nickname;


    @Builder
    public FashionResDto(String imgUrl, String nickname) {
        this.imgUrl = imgUrl;
        this.nickname = nickname;

    }

    public personalColorResponseDto toEntity() {
        return personalColorResponseDto.builder()
                .personalColor(imgUrl)
                .nickname(nickname)
                .build();
    }
}
