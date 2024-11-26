package com.mysite.sbb.homepage;

import java.util.List;

import com.mysite.sbb.homepage.Homepage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import lombok.RequiredArgsConstructor;

@RequestMapping("/homepage")
@RequiredArgsConstructor
@Controller
public class HomepageController {


    @GetMapping("/stylerobo")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        return "homepage_stylerobo";
    }

}