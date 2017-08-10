package com.ringov.yamblzweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.BoolRes;
import android.support.annotation.StringRes;

/**
 * Helper class for shared preferences
 */
public final class Prefs {

    private Prefs() {
    }

    public static String getString(Context context, @StringRes int key, @StringRes int def) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String keyStr = context.getString(key);
        String defStr = context.getString(def);
        return prefs.getString(keyStr, defStr);
    }

    public static boolean getBoolean(Context context, @StringRes int key, @BoolRes int def) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String keyStr = context.getString(key);
        boolean defBool = context.getResources().getBoolean(def);
        return prefs.getBoolean(keyStr, defBool);
    }

    public static void putString(Context context, @StringRes int key, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String keyStr = context.getString(key);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(keyStr, value);
        editor.apply();
    }

    public static void putBoolean(Context context, @StringRes int key, boolean value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String keyStr = context.getString(key);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(keyStr, value);
        editor.apply();
    }
}
