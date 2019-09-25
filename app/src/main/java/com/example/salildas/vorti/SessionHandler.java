package com.example.salildas.vorti;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import java.util.Date;

public class SessionHandler {

    private static final String PREF_NAME = "UserSession";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ROLL = "roll";
    private static final String KEY_ROLE = "role";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_EMPTY = "";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    public SessionHandler(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    public void loginUser(String phone, String fullName, String email, String roll, String role, String gender) {
        mEditor.putString(KEY_PHONE, phone);
        mEditor.putString(KEY_FULL_NAME, fullName);
        mEditor.putString(KEY_EMAIL, email);
        mEditor.putString(KEY_ROLL, roll);
        mEditor.putString(KEY_ROLE, role);
        mEditor.putString(KEY_GENDER, gender);
        Date date = new Date();

        //Set user session for next 7 days
        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }

    public boolean isLoggedIn() {
        Date currentDate = new Date();

        long millis = mPreferences.getLong(KEY_EXPIRES, 0);

        /* If shared preferences does not have a value
         then user is not logged in
         */
        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);

        /* Check if session is expired by comparing
        current date and Session expiry date
        */
        return currentDate.before(expiryDate);
    }

    public User getUserDetails() {
        //Check if user is logged in first
        if (!isLoggedIn()) {
            return null;
        }
        User user = new User();
        user.setPhone(mPreferences.getString(KEY_PHONE, KEY_EMPTY));
        user.setFullName(mPreferences.getString(KEY_FULL_NAME, KEY_EMPTY));
        user.setEmail(mPreferences.getString(KEY_EMAIL, KEY_EMPTY));
        user.setRoll(mPreferences.getString(KEY_ROLL, KEY_EMPTY));
        user.setRole(mPreferences.getString(KEY_ROLE, KEY_EMPTY));
        user.setGender(mPreferences.getString(KEY_GENDER, KEY_EMPTY));
        user.setSessionExpiryDate(new Date(mPreferences.getLong(KEY_EXPIRES, 0)));

        return user;
    }

    public void logoutUser() {
        mEditor.clear();
        mEditor.commit();
    }

}
