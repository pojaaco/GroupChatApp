package com.group.chat.client.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginView() {
        setTitle("Chat Client - Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        panel.add(new JPanel());
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });
    }

    private void authenticateUser() {
        String username = usernameField.getText();
        // String password = new String(passwordField.getPassword());

        try {
            // Check authentication here
            JOptionPane.showMessageDialog(this, "Login successful!");
           
            new ChatView(username).setVisible(true);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid email or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
