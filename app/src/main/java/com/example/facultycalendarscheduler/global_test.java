package com.example.facultycalendarscheduler;

public class global_test {
    public static String username,password;

    public static Boolean test_username(String username){
        if(username.length()>0 && username.contains("@")){
            return true;
        }
        return false;
    }

    public static Boolean test_password(String password){
        if(password.length()>6 && password.length()<15){
            return true;
        }
        return false;
    }


}
