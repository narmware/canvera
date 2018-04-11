package com.narmware.patima.pojo;

/**
 * Created by rohitsavant on 19/03/18.
 */

public class SharedPhotoResponse {

    String response;
    SharedPhoto[] data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public SharedPhoto[] getData() {
        return data;
    }

    public void setData(SharedPhoto[] data) {
        this.data = data;
    }
}
