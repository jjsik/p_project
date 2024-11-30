package com.mysite.sbb.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "입력란에 내용을 모두 기입해주세요.")
    private String username;

    @NotEmpty(message = "입력란에 내용을 모두 기입해주세요.")
    private String password1;

    @NotEmpty(message = "입력란에 내용을 모두 기입해주세요.")
    private String password2;

    @NotEmpty(message = "입력란에 내용을 모두 기입해주세요.")
    private String name;

    @NotEmpty(message = "입력란에 내용을 모두 기입해주세요.")
    private String nickname;

    private String gender;
}
