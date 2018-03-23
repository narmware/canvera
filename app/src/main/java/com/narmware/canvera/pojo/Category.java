package com.narmware.canvera.pojo;

/**
 * Created by rohitsavant on 20/03/18.
 */

public class Category {

    //locally used
    String cat_name,cat_img;
    boolean available;

    //coming from server side
    String cat_title,cat_id;


    public Category(String cat_name, String cat_img,boolean is_available) {
        this.cat_name = cat_name;
        this.cat_img = cat_img;
        this.available=is_available;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public boolean is_available() {
        return available;
    }

    public void setIs_available(boolean is_available) {
        this.available = is_available;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_img() {
        return cat_img;
    }

    public void setCat_img(String cat_img) {
        this.cat_img = cat_img;
    }

    public String getCat_title() {
        return cat_title;
    }

    public void setCat_title(String cat_title) {
        this.cat_title = cat_title;
    }
}
