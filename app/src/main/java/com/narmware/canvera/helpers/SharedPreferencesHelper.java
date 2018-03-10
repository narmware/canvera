package com.narmware.canvera.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by comp16 on 12/19/2017.
 */

public class SharedPreferencesHelper {
    private static final String LOGIN_PREF="login";
    private static final String IS_GRID="isGrid";


    public static void setLogin(boolean login, Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit=pref.edit();
        edit.putBoolean(LOGIN_PREF,login);
        edit.commit();
    }

    public static boolean getLogin(Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        boolean login=pref.getBoolean(LOGIN_PREF,false);
        return login;
    }

    public static void setIsGrid(boolean isGrid, Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit=pref.edit();
        edit.putBoolean(IS_GRID,isGrid);
        edit.commit();
    }

    public static boolean getIsGrid(Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        boolean isGrid=pref.getBoolean(IS_GRID,false);
        return isGrid;
    }
}
