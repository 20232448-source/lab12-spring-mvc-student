package vn.edu.eaut.lab12.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("title", "Trang chủ");
        model.addAttribute("message", "Chào mừng đến với Spring Boot và Thymeleaf");
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "Giới thiệu");
        model.addAttribute("description", "Ứng dụng minh họa Spring Boot kết hợp Thymeleaf.");
        return "about";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("title", "Liên hệ");
        return "contact";
    }

    @GetMapping("/courses")
    public String courses(Model model) {
        model.addAttribute("title", "Khóa học");
        model.addAttribute("courses", List.of(
                "Lập trình Java cơ bản",
                "Spring Boot và Thymeleaf",
                "Cơ sở dữ liệu quan hệ",
                "Phát triển ứng dụng web"
        ));
        return "courses";
    }

}
