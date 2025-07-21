package pproject.stylelobo.domain.table;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "favorite_fashion_results")
public class FavoriteFashionResults {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private Long resultId;

    @Column(name = "selected_styles", nullable = false) // color_type 컬럼 매핑
    private String selectedStyles;

    @Column(name = "preferred_style_input", columnDefinition = "TEXT") // TEXT 매핑
    private String preferredStyleInput;

    @Column(name = "selected_faces", columnDefinition = "TEXT") // TEXT 매핑
    private String selectedFaces;

    @Column(name = "selected_bodys", columnDefinition = "TEXT") // TEXT 매핑
    private String selectedBodys;

    @Lob // LONGBLOB 매핑
    @Column(name = "diagnosed_style", columnDefinition = "MEDIUMBLOB", nullable = false) // TEXT 매핑
    private byte[] diagnosedStyle;

    @Column(name = "created_at", insertable = false, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 외래 키 매핑: user_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id") // Users 테이블의 user_id와 매핑
    private Users user;

//    @OneToMany(mappedBy = "favoriteFashionResult", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<MyStyleSaved> myStyleSaveds;

    @Builder
    public FavoriteFashionResults(String selectedStyles, String preferredStyleInput, byte[] diagnosedStyle,
                                    String selectedFaces, String selectedBodys, Users user) {
        this.selectedStyles = selectedStyles;
        this.preferredStyleInput = preferredStyleInput;
        this.diagnosedStyle = diagnosedStyle;
        this.selectedFaces = selectedFaces;
        this.selectedBodys = selectedBodys;
        this.user = user;
    }
}
