package pproject.stylelobo.domain.table;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "my_style_saved")
public class MyStyleSaved {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "save_id")
    private Long save_id;


    // 외래 키 매핑: user_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id") // Users 테이블의 user_id와 매핑
    private Users user;

    // 외래 키 매핑: personal_color_result_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personalColorResultId", referencedColumnName = "result_id") // personalColor 테이블의 result_id와 매핑
    private Personal_Color personalColor;

    // 외래 키 매핑: favorite_fashion_result_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "favoriteFashionResultId", referencedColumnName = "result_id") // favoriteFashionResults 테이블의 result_id와 매핑
    private FavoriteFashionResults favoriteFashionResults;

    @Builder
    public MyStyleSaved(Users user, Personal_Color personalColor, FavoriteFashionResults favoriteFashionResults) {
        this.user = user;
        this.personalColor = personalColor;
        this.favoriteFashionResults = favoriteFashionResults;
    }
}
