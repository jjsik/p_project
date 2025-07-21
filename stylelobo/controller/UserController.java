package pproject.stylelobo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pproject.stylelobo.domain.dto.StatusDTO;
import pproject.stylelobo.domain.dto.isLoginDto;
import pproject.stylelobo.domain.table.Users;
import pproject.stylelobo.repository.UsersRepository;
import pproject.stylelobo.services.UsersService;
import pproject.stylelobo.status.DefaultRes;
import pproject.stylelobo.status.ResponseMessage;
import pproject.stylelobo.status.StatusCode;

import java.nio.charset.Charset;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UsersService usersService;

    private void errorPrint(Exception e) {
        ZonedDateTime zId = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        System.out.println( "[발생 시간] : " + zId.format(formatter) + "  에러 발생!!!!! : " + e.getMessage());
    }

    @GetMapping("/hello")
    public String hello(){
        return "아이디";
    }

    @PostMapping("/DuplicateTest")
    public ResponseEntity<StatusDTO> DuplicateTest(@RequestBody Users user)
    {
        StatusDTO dto = new StatusDTO();

        if(usersService.userFindByUserName(user.getUserName()) != null)
            dto.setStatus(0);
        else
            dto.setStatus(1);
        try
        {
            switch (dto.getStatus())
            {
                case 0:
                    dto.setMessage("중복된 아이디입니다.");
                    return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.ID_DUPLICATETEST_FAIL, dto), HttpStatus.OK);
                case 1:
                    dto.setMessage("가입 가능한 아이디입니다.");
                    break;
                default:
                    dto.setMessage("오류 발생");
                    dto.setErrCode("UserAPIController DuplicateTest쪽 확인바람");
                    return new ResponseEntity(DefaultRes.res(StatusCode.OK,ResponseMessage.INTERNAL_SERVER_ERROR, dto), HttpStatus.OK);

            }
        }
        catch (Exception e)
        {
            errorPrint(e);
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK,ResponseMessage.ID_DUPLICATETEST_SUCCESS, dto), HttpStatus.OK);
    }
    @GetMapping("/check-nickname")
    public ResponseEntity<DefaultRes<StatusDTO>> checkNickName(@RequestParam String nickname) {
        StatusDTO dto = new StatusDTO();

        if (usersService.userFindByNickName(nickname) != null) {
            dto.setStatus(0); // 중복된 닉네임
            dto.setMessage("이미 존재하는 닉네임입니다.");
        } else {
            dto.setStatus(1); // 사용 가능한 닉네임
            dto.setMessage("사용 가능한 닉네임입니다.");
        }

        return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, dto.getMessage(), dto), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<DefaultRes<StatusDTO>> signup(@RequestBody Users user) {
        StatusDTO dto = new StatusDTO();

        try {
            usersService.create(user);
            dto.setStatus(1);
            dto.setMessage("회원가입 성공");
        } catch (Exception e) {
            errorPrint(e);
            dto.setStatus(0);
            dto.setMessage("회원가입 실패");
            dto.setErrCode("서버쪽 오류");
            return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, ResponseMessage.CREATED_USER_FAIL, dto), HttpStatus.OK);
        }

        return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, ResponseMessage.CREATED_USER, dto), HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<StatusDTO> login(@RequestBody Users user, HttpSession session) {

        boolean isLogin = Optional.ofNullable((Boolean) session.getAttribute("isLogin")).orElse(false);

        if (!isLogin) {
            StatusDTO dto = new StatusDTO();
            dto.setStatus(usersService.login(user.getUserName(), user.getPassword()));

            String resMsg = null;

            try
            {
                switch (dto.getStatus())
                {
                    case 0:
                        dto.setMessage("아이디 오류");
                        resMsg = ResponseMessage.LOGIN_FAIL;
                        break;
                    case 1:
                        dto.setMessage("로그인 성공");
                        resMsg = ResponseMessage.LOGIN_SUCCESS;
                        //세션추가
                        session.setAttribute("username" , user.getUserName());
                        session.setAttribute("isLogin", true);
                        break;
                    case 2:
                        dto.setMessage("비밀번호 오류");
                        resMsg = ResponseMessage.LOGIN_FAIL;
                        break;
                    default:
                        resMsg = ResponseMessage.LOGIN_FAIL;
                        dto.setStatus(-1);
                        dto.setMessage("오류 발생");
                        dto.setErrCode("UserAPIController login 함수 쪽 확인 바람");
                }
            }catch(Exception e)
            {
                errorPrint(e);
            }

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMsg, dto), HttpStatus.OK);
        }
        else {
            StatusDTO dto = new StatusDTO();

            dto.setMessage("already login");
            String resMsg = ResponseMessage.LOGIN_FAIL;

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMsg, dto), HttpStatus.OK);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<StatusDTO> logout(HttpSession session) {

        StatusDTO dto = new StatusDTO();

        dto.setMessage("로그아웃 성공");
        session.invalidate();

        String resMsg = ResponseMessage.LOGOUT_SUCCESS;


        return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMsg, dto), HttpStatus.OK);
    }

    @GetMapping("/isLogin")
    public ResponseEntity<isLoginDto> isLogin(HttpSession session){
        boolean isLogin = Optional.ofNullable((Boolean) session.getAttribute("isLogin")).orElse(false);

        if(isLogin){
            String userName = (String) session.getAttribute("username");

            Users user = usersService.userFindByUserName(userName);

            isLoginDto dto = new isLoginDto(isLogin, user.getNickName(), user.getGender());

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER, dto), HttpStatus.OK);
        }
        else
            return new ResponseEntity(DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.NOT_FOUND_USER), HttpStatus.OK);
    }
}