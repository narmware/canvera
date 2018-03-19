package com.narmware.canvera.pojo;

/**
 * Created by rohitsavant on 19/03/18.
 */

public class MyPhotoResponse {

    String response;
    MyPhoto[] data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public MyPhoto[] getData() {
        return data;
    }

    public void setData(MyPhoto[] data) {
        this.data = data;
    }
}
