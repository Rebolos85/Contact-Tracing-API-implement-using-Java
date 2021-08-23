package com.contact.tracing.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.contact.tracing.R;

public class SessionManager {

    private  Context context;

    private final static String USER_TOKEN = "user_token";


    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    static SharedPreferences sharedPreferences;

    public  void saveUserAuthToken(String userToken) {
        SharedPreferences.Editor preferences = sharedPreferences.edit();
        Toast.makeText(context, "HOY NARA IMOHA TOKEN" + userToken, Toast.LENGTH_SHORT).show();
        preferences.putString(USER_TOKEN, userToken);
        preferences.apply();
    }

    public String fetchAuthToken() {

        Toast.makeText(context, "user token nimo bay " + sharedPreferences.getString(USER_TOKEN, null), Toast.LENGTH_SHORT).show();
        return sharedPreferences.getString(USER_TOKEN, null);
    }
}
