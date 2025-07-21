package pproject.stylelobo.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pproject.stylelobo.domain.dto.MyStyleColorDetailDto;
import pproject.stylelobo.domain.dto.MyStyleFashionDetailDto;
import pproject.stylelobo.domain.table.FavoriteFashionResults;
import pproject.stylelobo.domain.table.MyStyleSaved;
import pproject.stylelobo.domain.table.Personal_Color;
import pproject.stylelobo.domain.table.Users;
import pproject.stylelobo.repository.FavoriteFashionResultsRepository;
import pproject.stylelobo.repository.MyStyleSavedRepository;
import pproject.stylelobo.repository.PersonalColorResultsRepository;
import pproject.stylelobo.repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class MyStyleSavedService {
    private final MyStyleSavedRepository myStyleSavedRepository;
    private final UsersRepository usersRepository;
    private final FavoriteFashionResultsRepository favoriteFashionResultsRepository;
    private final PersonalColorResultsRepository personalColorResultsRepository;

    @Transactional
    public void saveMyStyle(Users user, Personal_Color personalColor, FavoriteFashionResults favoriteFashionResults){

        MyStyleSaved myStyleSaved = new MyStyleSaved(user, personalColor, favoriteFashionResults);

        myStyleSavedRepository.save(myStyleSaved);
    }


    public List<MyStyleFashionDetailDto> fashionMyStyle(Long userId){

        List<Long> resultIds = myStyleSavedRepository.findFashionByUserId(userId);

        List<MyStyleFashionDetailDto> myStyleFashionDetailDtos = new ArrayList<>();

        for(Long l : resultIds) {
            MyStyleFashionDetailDto dto = favoriteFashionResultsRepository.findUserDetailsByResultId(l);

            myStyleFashionDetailDtos.add(dto);
        }

        return myStyleFashionDetailDtos;
    }

    public List<MyStyleColorDetailDto> colorMyStyle(Long userId){

        List<Long> resultIds = myStyleSavedRepository.findColorByUserId(userId);

        List<MyStyleColorDetailDto> MyStyleColorDetailDtos = new ArrayList<>();

        for(Long l : resultIds) {
            MyStyleColorDetailDto dto = personalColorResultsRepository.findUserDetailsByResultId(l);

            MyStyleColorDetailDtos.add(dto);
        }

        return MyStyleColorDetailDtos;
    }
}
