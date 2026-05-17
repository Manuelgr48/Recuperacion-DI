package com.liceolapaz.mgr.jugadores2ev.recu_di.service;

import com.liceolapaz.mgr.jugadores2ev.recu_di.dao.UserDAO;
import com.liceolapaz.mgr.jugadores2ev.recu_di.model.Role;
import com.liceolapaz.mgr.jugadores2ev.recu_di.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
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
        if (username.trim().isEmpty() || email.trim().isEmpty() || password.isEmpty()) return "All fields are required.";
        if (!password.equals(checkPassword)) return "Passwords do not match.";
        if (password.length() < 6) return "Password must be at least 6 characters long.";

        boolean hasLetter = false, hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) hasLetter = true;
            if (Character.isDigit(c)) hasDigit = true;
        }
        if (!hasLetter || !hasDigit) return "Password must contain letters and numbers.";
        if (userDAO.findByUsername(username).isPresent()) return "Username is taken.";

        User newUser = new User(username, email, hashPassword(password), Role.USER);
        return userDAO.save(newUser) ? null : "Database error.";
    }


    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public boolean deleteUser(int id) {
        return userDAO.delete(id);
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