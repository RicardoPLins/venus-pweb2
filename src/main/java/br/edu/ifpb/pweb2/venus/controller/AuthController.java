package br.edu.ifpb.pweb2.venus.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/login")
    public ModelAndView getForm(ModelAndView modelAndView) {
        modelAndView.setViewName("auth/formlogin");
        return modelAndView;
    }

    @PostMapping("/logout")
    public ModelAndView logout(ModelAndView modelAndView) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        modelAndView.setViewName("redirect:/auth/login?logout");
        return modelAndView;
    }

}
