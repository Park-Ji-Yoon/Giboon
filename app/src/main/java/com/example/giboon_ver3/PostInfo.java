package com.example.giboon_ver3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostInfo implements Serializable {
    private String title;
    private ArrayList<String> contents;
    private String writer;
    private Date createdAt;

    public PostInfo(String title, ArrayList<String> contents, String writer, Date createdAt){
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.createdAt = createdAt;
    }

//    public PostInfo(String title, ArrayList<String> contents, ArrayList<String> formats, String publisher, Date createdAt){
//        this.title = title;
//        this.contents = contents;
//        this.writer = publisher;
//        this.createdAt = createdAt;
//    }

    public Map<String, Object> getPostInfo(){
        Map<String, Object> docData = new HashMap<>();
        docData.put("title",title);
        docData.put("contents",contents);
        docData.put("writer",writer);
        docData.put("createdAt",createdAt);
        return  docData;
    }

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public ArrayList<String> getContents(){
        return this.contents;
    }
    public void setContents(ArrayList<String> contents){
        this.contents = contents;
    }
    public String getPublisher(){
        return this.writer;
    }
    public void setPublisher(String publisher){
        this.writer = publisher;
    }
    public Date getCreatedAt(){
        return this.createdAt;
    }
    public void setCreatedAt(Date createdAt){
        this.createdAt = createdAt;
    }
}
