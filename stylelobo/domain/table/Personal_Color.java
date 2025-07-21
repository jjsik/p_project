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
@Table(name = "personal_color_results")
public class Personal_Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private Long resultId;

    @Column(name = "color_type", length = 50, nullable = false) // color_type 컬럼 매핑
    private String colorType;

    @Column(name = "recommendations", columnDefinition = "TEXT", nullable = false) // TEXT 매핑
    private String recommendations;

    @Lob // LONGBLOB 매핑
    @Column(name = "face_image", columnDefinition = "MEDIUMBLOB")
    private byte[] faceImage;


    @Column(name = "created_at", insertable = false, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 외래 키 매핑: user_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id") // Users 테이블의 user_id와 매핑
    private Users user;

    @Builder
    public Personal_Color(String colorType, String recommendations, byte[] faceImage, Users user) {
        this.colorType = colorType;
        this.recommendations = recommendations;
        this.faceImage = faceImage;
        this.user = user;
    }

//    @OneToMany(mappedBy = "personalColorResult", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<MyStyleSaved> myStyleSaveds;
}
