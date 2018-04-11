package com.narmware.patima.pojo;

/**
 * Created by rohitsavant on 23/03/18.
 */

public class CategoryResponse {
    String response;
    Category[] data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Category[] getData() {
        return data;
    }

    public void setData(Category[] data) {
        this.data = data;
    }
}
