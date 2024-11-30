package com.mysite.sbb.user.DTO;

import com.mysite.sbb.user.config.ImageConfig;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class AIFashionDTO implements Serializable {
    private String prompt;
    private int n;
    private String size;

    @Builder
    public AIFashionDTO(String prompt, int n)
    {
        this.prompt = prompt;
        this.n = 1;
        this.size = ImageConfig.IMAGE_SIZE;
    }
}
