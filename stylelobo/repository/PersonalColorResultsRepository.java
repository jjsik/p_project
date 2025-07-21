package pproject.stylelobo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pproject.stylelobo.domain.dto.MyStyleColorDetailDto;
import pproject.stylelobo.domain.dto.MyStyleFashionDetailDto;
import pproject.stylelobo.domain.table.Personal_Color;

import java.util.List;
import java.util.Optional;

public interface PersonalColorResultsRepository extends JpaRepository<Personal_Color, Long> {

    Optional<Personal_Color> findByResultId(Long result_id);

    @Query("""
        SELECT new pproject.stylelobo.domain.dto.MyStyleColorDetailDto(
            u.nickName,
            p.colorType, p.faceImage, p.recommendations, p.createdAt
        )
        FROM Personal_Color p
        JOIN p.user u
        WHERE p.resultId = :resultId
    """)
    MyStyleColorDetailDto findUserDetailsByResultId(@Param("resultId") Long resultId);
}
