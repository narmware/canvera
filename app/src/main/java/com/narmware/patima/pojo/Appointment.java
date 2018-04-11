package com.narmware.patima.pojo;

/**
 * Created by rohitsavant on 27/03/18.
 */

public class Appointment {

    String event_type,event_desc,event_loc,user_id,phm_id,start_date,end_date,response;

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getEvent_desc() {
        return event_desc;
    }

    public void setEvent_desc(String event_desc) {
        this.event_desc = event_desc;
    }

    public String getEvent_loc() {
        return event_loc;
    }

    public void setEvent_loc(String event_loc) {
        this.event_loc = event_loc;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPhm_id() {
        return phm_id;
    }

    public void setPhm_id(String phm_id) {
        this.phm_id = phm_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
