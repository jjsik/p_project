package pproject.stylelobo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pproject.stylelobo.domain.dto.MyStyleFashionDetailDto;
import pproject.stylelobo.domain.table.FavoriteFashionResults;
import pproject.stylelobo.domain.table.MyStyleSaved;

import java.util.List;
import java.util.Optional;

public interface FavoriteFashionResultsRepository extends JpaRepository<FavoriteFashionResults, Long> {

    Optional<FavoriteFashionResults> findByUserId(Long result_id);

    @Query("""
        SELECT new pproject.stylelobo.domain.dto.MyStyleFashionDetailDto(
            u.nickName,
            f.selectedStyles, f.diagnosedStyle, f.createdAt, f.selectedFaces, f.selectedBodys
        )
        FROM FavoriteFashionResults f
        JOIN f.user u
        WHERE f.resultId = :resultId
    """)
    MyStyleFashionDetailDto findUserDetailsByResultId(@Param("resultId") Long resultId);

}
