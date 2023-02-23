package com.dmitrySheyko.app.services;

import com.dmitrySheyko.web.dto.LoginForm;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final Logger logger = Logger.getLogger(LoginService.class);

    public boolean authenticate (LoginForm loginForm){
        logger.info("try authenticate");
        return loginForm.getUsername().equals("root") && loginForm.getPassword().equals("123");
    }

}
