package com.mysite.sbb.user.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
public class ImageResDTO {

    private long created;
    private List<ImageURL> data;

    @Getter
    @Setter
    public static class ImageURL {
        private String url;
    }
}
