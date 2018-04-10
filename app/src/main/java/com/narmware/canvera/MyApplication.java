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
    public static final String URL_MY_ALBUM=URL_SERVER+"album.php";
    public static final String URL_ALBUM_GALLERY=URL_SERVER+"album_data.php";
    public static final String URL_SHARED_ALBUM=URL_SERVER+"shared.php";
    public static final String URL_VALIDATE_ALBUM=URL_SERVER+"sharedalbum.php";
    public static final String URL_GET_CATEGORIES=URL_SERVER+"portfolio-category.php";
    public static final String URL_USER_LOGIN=URL_SERVER+"user.php";
    public static final String URL_GET_CATEGORY_ALBUM=URL_SERVER+"category-img-video.php";

    public static final String URL_BOOK_APPOINTMENT=URL_SERVER+"book_appointment.php";
    public static final String URL_FEEDBACK=URL_SERVER+"feedback.php";


}
