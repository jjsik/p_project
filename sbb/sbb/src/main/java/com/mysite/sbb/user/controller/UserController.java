package com.mysite.sbb.user.controller;

import com.mysite.sbb.user.UserCreateForm;
import com.mysite.sbb.user.service.UserService;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.dao.DataIntegrityViolationException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }
        try {
            userService.create(userCreateForm.getUsername(),
                    userCreateForm.getName(),userCreateForm.getNickname()
                    ,userCreateForm.getPassword1(), userCreateForm.getGender());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }
        return "redirect:/";
    }



    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @GetMapping("/findUsername")
    public String findUsername() {
        return "findUsername_form";
    }

    @PostMapping("/findUsername")
    public String findUsername(@RequestParam("name") String email, Model model) {

       String username = this.userService.findUsername(email);
       if (username != null) {
            model.addAttribute("username", username);
            // 테스트 삭제해도 됨
            System.out.println("1111111111111111111" + username);
       } else {
            model.addAttribute("errorMessage", "해당 이메일로 가입된 사용자를 찾을 수 없습니다.");
       }
        return "findUsername_result";
    }

//    @GetMapping("/findPassword")
//    public String findPassword() {
//        return "findPassword_form";
//    }
//
//    @PostMapping("/findPassword")
//    public String findPassword(@RequestParam String username, @RequestParam String name, Model model) {
//        boolean result = this.userService.findPassword(username, name);
//        if (result) {
//            // TODO: 임시 비밀번호 생성 및 이메일 발송 로직 추가
//            model.addAttribute("message", "임시 비밀번호가 이메일로 발송되었습니다.");
//        } else {
//            model.addAttribute("errorMessage", "해당 정보로 가입된 사용자를 찾을 수 없습니다.");
//        }
//        return "findPassword_result";
//    }
}