package com.liceolapaz.mgr.jugadores2ev.recu_di.service;

import com.liceolapaz.mgr.jugadores2ev.recu_di.dao.UserDAO;
import com.liceolapaz.mgr.jugadores2ev.recu_di.model.Role;
import com.liceolapaz.mgr.jugadores2ev.recu_di.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

public class UserService {

    private final UserDAO userDAO = new UserDAO();
    public Optional<User> login(String username, String password) {
        Optional<User> userOpt = userDAO.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String hashedInput = hashPassword(password);
            if (user.getPassword().equals(hashedInput)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public String registerUser(String username, String email, String password, String checkPassword) {
        if (username.trim().isEmpty() || email.trim().isEmpty() || password.isEmpty()) {
            return "All fields are required.";
        }

        if (!password.equals(checkPassword)) {
            return "Passwords not match.";
        }
        if (password.length() < 6) {
            return "Password must be at least 6 characters long.";
        }
        boolean hasLetter = false;
        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) hasLetter = true;
            if (Character.isDigit(c)) hasDigit = true;
        }
        if (!hasLetter || !hasDigit) {
            return "Password must contain both letters and numbers.";
        }

        if (userDAO.findByUsername(username).isPresent()) {
            return "Username is already taken.";
        }

        String hashedPassword = hashPassword(password);
        User newUser = new User(username, email, hashedPassword, Role.USER);

        boolean saved = userDAO.save(newUser);
        return saved ? null : "Database error during registration.";
    }

    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}