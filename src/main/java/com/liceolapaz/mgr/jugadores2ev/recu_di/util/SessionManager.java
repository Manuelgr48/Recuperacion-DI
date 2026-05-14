package com.liceolapaz.mgr.jugadores2ev.recu_di.util;

import com.liceolapaz.mgr.jugadores2ev.recu_di.model.User;

public class SessionManager {
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }
}