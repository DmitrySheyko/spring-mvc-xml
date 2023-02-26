package com.dmitrySheyko.web.controllers;

import com.dmitrySheyko.app.exceptions.BookShelfLoginException;
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

    private final Logger logger = Logger.getLogger(LoginController.class);

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping()
    public String login(Model model) {
        logger.info(" ");
        model.addAttribute("loginForm", new LoginForm());
        return "login_page";
    }

    @PostMapping("/auth")
    public String authenticate(LoginForm loginform) throws BookShelfLoginException {
        if (loginService.authenticate(loginform)) {
            logger.info("Success login by user id=" + loginform.getUsername());
            return "redirect:/books/shelf";
        } else {
            logger.info("Fail login by user id=" + loginform.getUsername());
            throw new BookShelfLoginException("Invalid username or password");
        }
    }

}
