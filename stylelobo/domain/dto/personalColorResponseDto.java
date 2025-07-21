package pproject.stylelobo.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pproject.stylelobo.domain.Gender;

@Getter
@Setter
public class personalColorResponseDto {
    private String personalColor;
    private String nickname;
    private String recommendations;
    private Gender gender;

    @Builder
    public personalColorResponseDto(String personalColor, String nickname, String recommendations, Gender gender) {
        this.personalColor = personalColor;
        this.nickname = nickname;
        this.recommendations = recommendations;
        this.gender = gender;
    }

    public personalColorResponseDto toEntity() {
        return personalColorResponseDto.builder()
                .personalColor(personalColor)
                .nickname(nickname)
                .recommendations(recommendations)
                .gender(gender)
                .build();
    }
}
