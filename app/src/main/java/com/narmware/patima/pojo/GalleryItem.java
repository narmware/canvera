package com.narmware.patima.pojo;

/**
 * Created by savvy on 3/9/2018.
 */

public class GalleryItem {
    String img_path,img_type;

    public GalleryItem(String img_path) {
        this.img_path = img_path;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getImg_type() {
        return img_type;
    }

    public void setImg_type(String img_type) {
        this.img_type = img_type;
    }
}

