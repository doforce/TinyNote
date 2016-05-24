package com.example.vampire.tinynote.data;

/**
 * Created by Vampire on 2016/5/1 0001
 * 自定义的MyData
 */
public class Mydata {
    private int Id;
    private String title;
    private String content;
    private String time;
    private int countTime;

    public Mydata(int ID, String title, String content, String time, int countTime) {
        this.Id = ID;
        this.title = title;
        this.content = content;
        this.time = time;
        this.countTime = countTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCountTime() {
        return countTime;
    }

    public void setCountTime(int countTime) {
        this.countTime = countTime;
    }


    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }

}
