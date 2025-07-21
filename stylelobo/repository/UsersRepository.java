package pproject.stylelobo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pproject.stylelobo.domain.table.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUserName(String userName);

    Optional<Users> findByNickName(String nickName);
}