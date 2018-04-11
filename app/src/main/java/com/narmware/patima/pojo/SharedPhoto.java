package com.narmware.patima.pojo;

/**
 * Created by savvy on 1/5/2018.
 */

public class SharedPhoto {

    String photo_title,photo_path,photo_desc,album_id,username;

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

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
