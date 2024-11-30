package com.mysite.sbb.user.DTO;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FavoriteFashionResultDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long result_id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="id")
    private SiteUser user_id;

    private String selected_styles;

    private String preferred_style_input;

    private String diagnosed_style;

    private String created_at;

}
