package pproject.stylelobo.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReactController {

    @RequestMapping(value = {"/{path:[^\\.]*}", "/**/{path:[^\\.]*}"})
    public String forward() {
        // 모든 비-API 경로를 index.html로 리다이렉트
        return "forward:/index.html";
    }
}
