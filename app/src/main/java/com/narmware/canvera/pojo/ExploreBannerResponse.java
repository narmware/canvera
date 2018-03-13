package com.narmware.canvera.pojo;

/**
 * Created by savvy on 3/13/2018.
 */

public class ExploreBannerResponse {
    String response;
    ExploreBanner[] data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public ExploreBanner[] getData() {
        return data;
    }

    public void setData(ExploreBanner[] data) {
        this.data = data;
    }
}
