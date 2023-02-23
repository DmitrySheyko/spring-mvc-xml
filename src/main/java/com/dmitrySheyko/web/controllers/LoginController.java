package com.dmitrySheyko.web.controllers;

import com.dmitrySheyko.app.services.LoginService;
import com.dmitrySheyko.web.dto.LoginForm;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/login")
public class LoginController {

    private Logger logger = Logger.getLogger(LoginController.class);

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping()
    public String login(Model model){
        logger.info("GET method");
        model.addAttribute("loginForm", new LoginForm());
        return "login_page";
    }

    @PostMapping("/auth")
    public String authenticate(LoginForm loginform){
        if(loginService.authenticate(loginform)) {
            logger.info("Log Ok");
            return "redirect:/books/shelf";
        } else{
            logger.info("Log fail");
           return "redirect:/login";
        }
    }

}
