package com.narmware.canvera;

import android.app.Application;

import javax.inject.Singleton;

/**
 * Created by rohitsavant on 05/03/18.
 */

@Singleton
public class MyApplication extends Application {

    public static final String URL_SERVER="http://www.narmware.com/demo/canvera/api/";
    public static final String URL_BANNER=URL_SERVER+"explore_banner.php";
    public static final String URL_FEATURED_IMGS=URL_SERVER+"featuredimgpage.php";

}
