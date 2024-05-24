package com.group.chat.server;

import org.springframework.context.ApplicationContext;

import com.group.chat.server.service.UserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ServerApplication.class, args);

        UserService userService = context.getBean(UserService.class);
        try {
            userService.registerUser("Bao", "123");
        } catch (Exception e) {
            System.out.println("Can't register");
        }

        try {
            userService.registerUser("Nhi", "123");
        } catch (Exception e) {
            System.out.println("Can't register");
        }
        
	}

}
