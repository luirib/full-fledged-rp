package pt.aveiro.nariz.fullfledgedrp.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.aveiro.nariz.fullfledgedrp.security.AppUser;

@Controller
@RequestMapping("/user")
public class AppUserController {
    @GetMapping
    String showUser(@AuthenticationPrincipal AppUser appUser, Model model) {
        model.addAttribute("appUser", appUser);
        return "app-user/user";
    }
}
