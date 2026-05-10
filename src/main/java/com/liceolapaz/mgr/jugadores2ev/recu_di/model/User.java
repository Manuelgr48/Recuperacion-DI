package com.liceolapaz.mgr.jugadores2ev.recu_di.model;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private Role role;
    private int favoriteCarId;
    public User() {}

    public User(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public int getFavoriteCarId() { return favoriteCarId; }
    public void setFavoriteCarId(int favoriteCarId) { this.favoriteCarId = favoriteCarId; }
}