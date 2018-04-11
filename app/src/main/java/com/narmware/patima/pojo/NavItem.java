package com.narmware.patima.pojo;

/**
 * Created by rohitsavant on 22/03/18.
 */

public class NavItem {
    int img_id;
    String title;

    public NavItem(int img_id, String title) {
        this.img_id = img_id;
        this.title = title;
    }

    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
