package pproject.stylelobo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pproject.stylelobo.domain.table.MyStyleSaved;

import java.util.List;

public interface MyStyleSavedRepository extends JpaRepository<MyStyleSaved, Long> {

    // 특정 사용자의 저장된 스타일 찾기
    List<MyStyleSaved> findByUser_id(Long user_id);

//     조인 쿼리와 DTO 매핑
    @Query("""
        SELECT
            f.resultId
        FROM FavoriteFashionResults f
        JOIN f.user u
        WHERE u.id = :userId
    """)
    List<Long> findFashionByUserId(@Param("userId") Long userId);

    @Query("""
        SELECT
            p.resultId
        FROM Personal_Color p
        JOIN p.user u
        WHERE u.id = :userId
    """)
    List<Long> findColorByUserId(@Param("userId") Long userId);
}