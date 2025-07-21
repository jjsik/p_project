package pproject.stylelobo.domain.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pproject.stylelobo.config.ImageConfig;

import java.io.Serializable;

@Setter
@Getter
public class AiFashionDTO implements Serializable {

    private String prompt;
    private int n;
    private String size;
    private String model;

    @Builder
    public AiFashionDTO(String prompt, int n)
    {
        this.model = ImageConfig.MODEL;
        this.prompt = prompt;
        this.n = 1;
        this.size = ImageConfig.IMAGE_SIZE;
    }


}
