package com.example.facultycalendarscheduler;

public class global_test {
    public static String username;
    public static String password;

    public static Boolean test_username(String username){
        return username.length() > 0 && username.contains("@");
    }

    public static Boolean test_password(String password){
        return password.length() > 6 && password.length() < 15;
    }


}
