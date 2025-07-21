package pproject.stylelobo.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pproject.stylelobo.domain.table.Personal_Color;
import pproject.stylelobo.domain.table.Users;
import pproject.stylelobo.repository.PersonalColorResultsRepository;
import pproject.stylelobo.repository.UsersRepository;

@AllArgsConstructor
@Service
public class PersonalColorServices {

    private PersonalColorResultsRepository personalColorResultsRepository;
    private UsersRepository usersRepository;
    public Personal_Color savePersonalColorResult(String colorType, String recommendations, byte[] faceImage, Users user){

        Personal_Color personalColor = new Personal_Color(colorType, recommendations, faceImage, user);

        return personalColorResultsRepository.save(personalColor);
    }
}
