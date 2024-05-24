package com.group.chat.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group.chat.server.model.User;
import com.group.chat.server.service.UserService;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestParam String username, @RequestParam String password) throws Exception {
        return userService.registerUser(username, password);
    }

    @PostMapping("/login")
    public User login(@RequestParam String username, @RequestParam String password) throws Exception {
        return userService.loginUser(username, password);
    }
    
}
