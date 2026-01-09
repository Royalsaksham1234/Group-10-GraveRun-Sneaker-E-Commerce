/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Lenovo
 */
public class UserSession {
    private static boolean loggedIn = false;
    private static int userId;

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void login(int id) {
        loggedIn = true;
        userId = id;
    }

    public static void logout() {
        loggedIn = false;
        userId = 0;
    }

    public static int getUserId() {
        return userId;
    }
}

