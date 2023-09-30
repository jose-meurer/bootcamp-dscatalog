package dev.josemeurer.jwt.controllers;

import dev.josemeurer.jwt.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthContoller {

    private static final Logger LOG = LoggerFactory.getLogger(AuthContoller.class);

    @Autowired
    private TokenService tokenService;

    @PostMapping("/token")
    public String token(Authentication auth) {
        LOG.info("Token requested for user: '{}'", auth.getName());
        String token = tokenService.generateToken(auth);
        LOG.info("Token granted: {}", token);
        return token;
    }
}
