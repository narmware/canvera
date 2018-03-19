package com.narmware.canvera.pojo;

/**
 * Created by rohitsavant on 19/03/18.
 */

public class GalleryItemResponse {
    String response;
    GalleryItem[] data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public GalleryItem[] getData() {
        return data;
    }

    public void setData(GalleryItem[] data) {
        this.data = data;
    }
}
