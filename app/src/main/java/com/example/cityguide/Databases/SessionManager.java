package com.example.cityguide.Databases;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    public static final String SESSION_USERSESSSION = "userLoginSession";
    public static final String SESSION_REMEMBERMESESSSION = "rememberMeSession";

    private static final String IsLogin = "IsLoggedIn";
    public static final String KEY_FULLNAME = "fullname";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_DATE = "date";
    public static final String KEY_PHONENO = "phoneNo";

    private static final String IsRememberMe = "IsRememberMe";
    public static final String KEY_SESSIONPASSWORD = "password";
    public static final String KEY_SESSIONPHONENO = "phoneNo";


    public SessionManager(Context _context,String sessionName){
        context = _context;
        sharedPreferences = context.getSharedPreferences(sessionName,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }
    public void createLoginSession(String fullname,String username,String email,String password,String gender,String date,String phoneNo){
        editor.putBoolean(IsLogin,true);

        editor.putString(KEY_FULLNAME,fullname);
        editor.putString(KEY_USERNAME,username);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PASSWORD,password);
        editor.putString(KEY_DATE,date);
        editor.putString(KEY_GENDER,gender);
        editor.putString(KEY_PHONENO,phoneNo);

        editor.commit();
    }
    public HashMap<String,String > getUserDetailfromSession(){
        HashMap<String,String > userData = new HashMap<>();
        userData.put(KEY_FULLNAME,sharedPreferences.getString(KEY_FULLNAME,null));
        userData.put(KEY_USERNAME,sharedPreferences.getString(KEY_USERNAME,null));
        userData.put(KEY_EMAIL,sharedPreferences.getString(KEY_EMAIL,null));
        userData.put(KEY_GENDER,sharedPreferences.getString(KEY_GENDER,null));
        userData.put(KEY_PASSWORD,sharedPreferences.getString(KEY_PASSWORD,null));
        userData.put(KEY_DATE,sharedPreferences.getString(KEY_DATE,null));
        userData.put(KEY_PHONENO,sharedPreferences.getString(KEY_PHONENO ,null));
        return userData;

    }
    public boolean checkLogin(){
        if (sharedPreferences.getBoolean(IsLogin,true)){
            return true;
        }else
        {
            return false;
        }
    }
    public void logoutUserFromSession(){
        editor.clear();
        editor.commit();
    }
    public void createRememberMeSession(String phoneNo,String password){
        editor.putBoolean(IsRememberMe,true);
        editor.putString(KEY_PASSWORD,password);
        editor.putString(KEY_PHONENO,phoneNo);

        editor.commit();
    }
    public HashMap<String,String > getRememberMeDetailfromSession(){
        HashMap<String,String > userData = new HashMap<>();
        userData.put(KEY_SESSIONPASSWORD,sharedPreferences.getString(KEY_SESSIONPASSWORD,null));
        userData.put(KEY_SESSIONPHONENO,sharedPreferences.getString(KEY_SESSIONPHONENO ,null));

        return userData;

    }
    public boolean checkRemembebrMe(){
        if (sharedPreferences.getBoolean(IsRememberMe,true)){
            return true;
        }else
        {
            return false;
        }
    }
}
