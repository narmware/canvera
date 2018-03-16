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
    private static final String LAST_FEATURD_IMG_ID="id";
    private static final String LAST_FEATURD_VID_ID="v_id";


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

    public static void setLastFeaturdImgId(String id, Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString(LAST_FEATURD_IMG_ID,id);
        edit.commit();
    }

    public static String getLastFeaturdImgId(Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        String id=pref.getString(LAST_FEATURD_IMG_ID,null);
        return id;
    }

    public static void setLastFeaturdVidId(String v_id, Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString(LAST_FEATURD_VID_ID,v_id);
        edit.commit();
    }

    public static String getLastFeaturdVidId(Context context)
    {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        String v_id=pref.getString(LAST_FEATURD_VID_ID,null);
        return v_id;
    }
}
