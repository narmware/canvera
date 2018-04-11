package com.narmware.patima.pojo;

/**
 * Created by savvy on 3/13/2018.
 */

public class TopTakesResponse {
    String response;
    TopTakes[] data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public TopTakes[] getData() {
        return data;
    }

    public void setData(TopTakes[] data) {
        this.data = data;
    }
}
