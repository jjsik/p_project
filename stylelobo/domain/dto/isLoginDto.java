package pproject.stylelobo.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pproject.stylelobo.domain.Gender;

@Getter
@Setter
public class isLoginDto {
    private boolean isLogin;
    private String nickName;
    private Gender gender;

    public isLoginDto(boolean isLogin, String nickName, Gender gender) {
        this.isLogin = isLogin;
        this.nickName = nickName;
        this.gender = gender;
    }
}
