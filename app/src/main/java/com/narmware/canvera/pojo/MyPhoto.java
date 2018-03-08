package com.narmware.canvera.pojo;

/**
 * Created by savvy on 1/5/2018.
 */

public class MyPhoto {

    String photo_title,photo_path;

    public MyPhoto(String photo_title, String photo_path) {
        this.photo_title = photo_title;
        this.photo_path = photo_path;
    }

    public String getPhoto_title() {
        return photo_title;
    }

    public void setPhoto_title(String photo_title) {
        this.photo_title = photo_title;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }
}
