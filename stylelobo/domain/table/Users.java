package pproject.stylelobo.domain.table;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pproject.stylelobo.domain.Gender;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", length = 50, nullable = false)
    private String userName;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(name = "nickname", length = 100, nullable = false)
    private String nickName;

    @Enumerated(EnumType.STRING) // Enum 값을 문자열로 저장
    @Column(length = 10, nullable = false)
    private Gender gender;

    @Builder
    public Users(String username, String password, String name, String nickname, Gender gender) {
        this.userName = username;
        this.password = password;
        this.name = name;
        this.nickName = nickname;
        this.gender = gender;
    }

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Personal_Color> personalColorResults;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<FavoriteFashionResults> FavoriteFashionResultsResults;
}
