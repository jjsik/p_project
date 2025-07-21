package pproject.stylelobo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pproject.stylelobo.domain.dto.MyStyleColorDetailDto;
import pproject.stylelobo.domain.dto.MyStyleFashionDetailDto;
import pproject.stylelobo.domain.dto.MyStyleResponseDto;
import pproject.stylelobo.domain.dto.StatusDTO;
import pproject.stylelobo.domain.table.Users;
import pproject.stylelobo.services.MyStyleSavedService;
import pproject.stylelobo.services.UsersService;
import pproject.stylelobo.status.DefaultRes;
import pproject.stylelobo.status.ResponseMessage;
import pproject.stylelobo.status.StatusCode;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/myStyle")
public class MyStyleController {
    @Autowired
    private MyStyleSavedService myStyleSavedService;
    @Autowired
    private UsersService usersService;


    @GetMapping("/color")
    public ResponseEntity<List<MyStyleColorDetailDto>> myStyleColor(HttpSession session){

        boolean isLogin = Optional.ofNullable((Boolean) session.getAttribute("isLogin")).orElse(false);


        if(isLogin){
            String userName = (String) session.getAttribute("username");

            Users user = usersService.userFindByUserName(userName);

            List<MyStyleColorDetailDto> myStyleColorDetailDtos = myStyleSavedService.colorMyStyle(user.getId());

            String resMsg = ResponseMessage.TRANSMISSION_SUCCESS;

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMsg, myStyleColorDetailDtos), HttpStatus.OK);
        }

        StatusDTO dto = new StatusDTO();

        dto.setMessage("login please");
        String resMsg = ResponseMessage.LOGIN_FAIL;

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMsg, dto), HttpStatus.OK);
    }

    @GetMapping("/fashion")
    public ResponseEntity<List<MyStyleFashionDetailDto>> myStyleFashion(HttpSession session){

        boolean isLogin = Optional.ofNullable((Boolean) session.getAttribute("isLogin")).orElse(false);

        if(isLogin){
            String userName = (String) session.getAttribute("username");

            Users user = usersService.userFindByUserName(userName);


            List<MyStyleFashionDetailDto> myStyleFashionDetailDtos = myStyleSavedService.fashionMyStyle(user.getId());

            // byte[] 데이터를 Base64로 변환
            for (MyStyleFashionDetailDto dto : myStyleFashionDetailDtos) {
                byte[] imageBytes = dto.getImageBytes(); // 데이터베이스에서 가져온 byte[]
                String base64Image = Base64.getEncoder().encodeToString(imageBytes); // Base64 변환
                dto.setDiagnosedStyle("data:image/png;base64," + base64Image); // Base64 URL로 설정
            }

            String resMsg = ResponseMessage.TRANSMISSION_SUCCESS;


            return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMsg, myStyleFashionDetailDtos), HttpStatus.OK);
        }

        StatusDTO dto = new StatusDTO();

        dto.setMessage("login please");
        String resMsg = ResponseMessage.LOGIN_FAIL;

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMsg, dto), HttpStatus.OK);
    }

    @GetMapping("/test")
    public List<MyStyleColorDetailDto> test(){

        Users user = usersService.userFindByUserName("lshyeong");
        return myStyleSavedService.colorMyStyle(user.getId());
    }
}
