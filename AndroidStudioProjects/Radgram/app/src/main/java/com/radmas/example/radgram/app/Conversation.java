package com.radmas.example.radgram.app;

/**
 * Created by raddev01 on 03/04/2014.
 */
public class Conversation {

    private String message;
    private String time;
    private String user;


    public Conversation() {
    }

    public Conversation(String message, String time, String user) {
        this.message = message;
        this.time = time;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "{" +
                "message='" + message + '\'' +
                ", time=" + time +
                ", user=" + user +
                '}';
    }
}
