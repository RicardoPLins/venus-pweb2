package br.edu.ifpb.pweb2.venus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/home")
    public String showHomePage() {
        return "index";
    }

    @RequestMapping("/login")
    public String showLoginPage() {
        return "/login/formLogin";
    }

}