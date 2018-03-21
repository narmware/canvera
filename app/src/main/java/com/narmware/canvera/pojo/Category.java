package com.narmware.canvera.pojo;

/**
 * Created by rohitsavant on 20/03/18.
 */

public class Category {

    String cat_name,cat_img;

    public Category(String cat_name, String cat_img) {
        this.cat_name = cat_name;
        this.cat_img = cat_img;
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
}
