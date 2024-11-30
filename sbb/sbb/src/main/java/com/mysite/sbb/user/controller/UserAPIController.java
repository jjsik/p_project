package com.mysite.sbb.user.controller;

import com.mysite.sbb.user.DTO.*;
import com.mysite.sbb.user.service.UserService;
import com.mysite.sbb.user.status.DefaultRes;
import com.mysite.sbb.user.status.ResponseMessage;
import com.mysite.sbb.user.status.StatusCode;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.nio.charset.Charset;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("userAPI")
public class UserAPIController {

    private final UserService userService;

    private void errorPrint(Exception e) {
        ZonedDateTime zId = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        System.out.println( "[발생 시간] : " + zId.format(formatter) + "  에러 발생!!!!! : " + e.getMessage());
    }
    @PostMapping("AIImage")
    public List<ImageResDTO> FashionType(@RequestBody FashionTypeDTO fas)
    {
        FashionTypeDTO fashionDTO = fas;
        String data[] = fashionDTO.getType().split(",");
        List<ImageResDTO> res = new ArrayList<>();

        System.out.println(fas.getType());

        if(data[0] != null && fashionDTO.getText() != null)
        {
            for(int i = 0; i < data.length; i++)
            {
                AIFashionDTO AIdto = new AIFashionDTO(data[i] + " 스타일 : " + fashionDTO.getText(), 1);
                System.out.println("1111111111111111111111111111111111:::::::::::::::::::::::" + AIdto.getPrompt());
                res.add(this.userService.FashionType(AIdto));
            }

        }
        else{

        }

        return res;
    }

    @PostMapping("DuplicateTest")
    public ResponseEntity<StatusDTO> DuplicateTest(@RequestBody SiteUser user)
    {
        StatusDTO dto = new StatusDTO();

        if(this.userService.findUsername(user.getUsername()) != null)
            dto.setStatus(0);
        else
            dto.setStatus(1);
        try
        {
            switch (dto.getStatus())
            {
                case 0:
                    dto.setMessage("중복된 아이디입니다.");
                    return new ResponseEntity(DefaultRes.res(StatusCode.OK,ResponseMessage.ID_DUPLICATETEST_FAIL, dto), HttpStatus.OK);
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

    @PostMapping("signupAPI")
    public ResponseEntity<StatusDTO> signup(@RequestBody SiteUser user) {

        StatusDTO dto = new StatusDTO();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        try
        {
            userService.create(user.getUsername(),
                    user.getName(),user.getNickname()
                    ,user.getPassword(), user.getGender());
        }
        catch (Exception e)
        {
            errorPrint(e);
            // 회원가입 실패
            dto.setStatus(0);
            dto.setMessage("회원가입 실패");
            dto.setErrCode("서버쪽 오류");
            return new ResponseEntity(DefaultRes.res(StatusCode.OK,ResponseMessage.CREATED_USER_FAIL, dto), HttpStatus.OK);
        }
        // 회원가입 성공
        dto.setStatus(1);
        dto.setMessage("회원가입 성공");

        return new  ResponseEntity(DefaultRes.res(StatusCode.OK,ResponseMessage.CREATED_USER, dto), HttpStatus.OK);
    }

    @PostMapping("loginAPI")
    public ResponseEntity<StatusDTO> login(@RequestBody SiteUser user, HttpSession session, Model model) {

        StatusDTO dto = new StatusDTO();
        dto.setStatus(this.userService.login(user.getUsername(), user.getPassword()));

        String resMsg = null;

        try
        {
            switch (dto.getStatus())
            {
                case 0:
                    dto.setMessage("로그인 실패");
                    resMsg = ResponseMessage.LOGIN_FAIL;
                    break;
                case 1:
                    dto.setMessage("로그인 성공");
                    resMsg = ResponseMessage.LOGIN_SUCCESS;
                    //세션추가
                    session.setAttribute("username" , user.getUsername());
                    break;
                case 2:
                    break;
                default:
                    resMsg = ResponseMessage.LOGIN_FAIL;
                    dto.setStatus(-1);
                    dto.setMessage("오류 발생");
                    dto.setErrCode("UserAPIController login함수 쪽 확인 바람");
            }
        }catch(Exception e)
        {
            errorPrint(e);
        }

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMsg, dto), HttpStatus.OK);
    }
}