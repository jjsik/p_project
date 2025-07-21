package pproject.stylelobo.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MyStyleResponseDto {
    private List<MyStyleFashionDetailDto> myStyleFashionDetailDtos;
    private List<MyStyleColorDetailDto> myStyleColorDetailDtos;

    public MyStyleResponseDto (List<MyStyleFashionDetailDto> myStyleFashionDetailDtos, List<MyStyleColorDetailDto> myStyleColorDetailDtos){
        this.myStyleFashionDetailDtos = myStyleFashionDetailDtos;
        this.myStyleColorDetailDtos = myStyleColorDetailDtos;
    }
}
