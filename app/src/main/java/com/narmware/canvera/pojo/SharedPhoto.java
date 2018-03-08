package com.narmware.canvera.pojo;

/**
 * Created by savvy on 1/5/2018.
 */

public class SharedPhoto {

    String photo_title,photo_path,photo_desc;

    public SharedPhoto(String photo_title, String photo_path, String photo_desc) {
        this.photo_title = photo_title;
        this.photo_path = photo_path;
        this.photo_desc = photo_desc;
    }

    public String getPhoto_desc() {
        return photo_desc;
    }

    public void setPhoto_desc(String photo_desc) {
        this.photo_desc = photo_desc;
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
