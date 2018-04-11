package com.narmware.patima.pojo;

/**
 * Created by savvy on 3/13/2018.
 */

public class TopVideoResponse {
    String response;
    VideoPojo2[] data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public VideoPojo2[] getData() {
        return data;
    }

    public void setData(VideoPojo2[] data) {
        this.data = data;
    }
}
