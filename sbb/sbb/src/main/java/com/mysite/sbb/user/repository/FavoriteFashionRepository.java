package com.mysite.sbb.user.repository;

import com.mysite.sbb.user.DTO.FavoriteFashionResultDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteFashionRepository extends JpaRepository<FavoriteFashionResultDTO, Long> {

}
