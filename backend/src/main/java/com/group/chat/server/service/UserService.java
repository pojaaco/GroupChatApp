package com.group.chat.server.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.group.chat.server.model.User;
import com.group.chat.server.repository.UserRepository;


@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password) throws Exception {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new Exception("Username already taken");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public User loginUser(String username, String password) throws Exception {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (!userOptional.isPresent() || !passwordEncoder.matches(password, userOptional.get().getPassword())) {
            throw new Exception("Invalid username or password");
        }

        return userOptional.get();
    }
    
}
