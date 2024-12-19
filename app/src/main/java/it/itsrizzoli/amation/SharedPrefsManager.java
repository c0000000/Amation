package it.itsrizzoli.amation;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsManager {

    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_USER_ID = "idUtente";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public SharedPrefsManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveUserId(int userId) {
        editor.putString(KEY_USER_ID, String.valueOf(userId));
        editor.apply();
    }

    public int getUserId() {
        return Integer.parseInt(sharedPreferences.getString(KEY_USER_ID, "-1"));
    }


    public void clearAll() {
        editor.clear();
        editor.apply();
    }
}
