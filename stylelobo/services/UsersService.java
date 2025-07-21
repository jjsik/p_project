package pproject.stylelobo.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pproject.stylelobo.domain.table.Users;
import pproject.stylelobo.repository.UsersRepository;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UsersService {

    private UsersRepository usersRepository;

    @Transactional
    public Users userFindByUserName(String userName) {
        return usersRepository.findByUserName(userName).orElse(null);
    }

    public Users create(Users user) {
        usersRepository.save(user);
        return user;
    }

    public int login(String username, String password) {
        Optional<Users> optionalUser = usersRepository.findByUserName(username);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            if (password.equals(user.getPassword())) return 1;
            else return 2;
        }
        return 0;
    }

    public Users userFindByNickName(String nickname) {
        return usersRepository.findByNickName(nickname).orElse(null); // Optional에서 값이 없으면 null 반환
    }
}