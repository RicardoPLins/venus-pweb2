package br.edu.ifpb.pweb2.venus.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.venus.service.UserService;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    // @RequestMapping("/home")
    // public String showHomePage() {
    //     return "index";
    // }

    @GetMapping
    public ModelAndView getHome(Principal principal, ModelAndView mav) {
        mav.setViewName("redirect:" + this.userService.home(principal));
        return mav;
    }

    @RequestMapping("/login")
    public String showLoginPage() {
        return "/login/formLogin";
    }

}