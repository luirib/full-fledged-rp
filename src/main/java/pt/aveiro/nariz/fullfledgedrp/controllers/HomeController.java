package pt.aveiro.nariz.fullfledgedrp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String home(@RequestParam(name = "logout", required = false, defaultValue = "true") String logout) {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "app-user/login";
    }
}
