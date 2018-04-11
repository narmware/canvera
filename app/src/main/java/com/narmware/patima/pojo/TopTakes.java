package com.narmware.patima.pojo;

/**
 * Created by rohitsavant on 12/03/18.
 */

public class TopTakes {

    String url,id,title;

    public TopTakes(String url, String id) {
        this.url = url;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
