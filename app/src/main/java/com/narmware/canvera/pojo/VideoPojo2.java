package com.narmware.canvera.pojo;

/**
 * Created by savvy on 12/7/2017.
 */

public class VideoPojo2 {
    String vtitle;
    String vpath;

    public VideoPojo2(String vtitle, String vpath) {
        this.vtitle = vtitle;
        this.vpath = vpath;
    }

    public String getVtitle() {
        return vtitle;
    }

    public void setVtitle(String vtitle) {
        this.vtitle = vtitle;
    }

    public String getVpath() {
        return vpath;
    }

    public void setVpath(String vpath) {
        this.vpath = vpath;
    }

    public static String getVideoId(String url) {
        return url.substring(32,url.length());
    }
}


