package com.example.giboon_ver3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NoticeInfo implements Serializable {
    private String title;
    private String contents;
    private String publisher;
    private Date createdAt;
    private String name;
    private String id;

    public NoticeInfo(String title, String contents, String publisher, Date createdAt, String name, String id){
        this.title = title;
        this.contents = contents;
        this.publisher = publisher;
        this.createdAt = createdAt;
        this.name = name;
        this.id = id;
    }

    public NoticeInfo(String title, String contents, String publisher, Date createdAt, String name) {
        this.title = title;
        this.contents = contents;
        this.publisher = publisher;
        this.createdAt = createdAt;
        this.name = name;
    }

    public Map<String, Object> getNoticeInfo(){
        Map<String, Object> docData2 = new HashMap<>();
        docData2.put("title2",title);
        docData2.put("contents2",contents);
        docData2.put("publisher2",publisher);
        docData2.put("createdAt2",createdAt);
        docData2.put("name2", name);
        return  docData2;
    }

    public String getTitle2(){
        return this.title;
    }
    public void setTitle2(String title){
        this.title = title;
    }
    public String getContents2(){
        return this.contents;
    }
    public void setContents2(String contents){
        this.contents = contents;
    }
    public String getPublisher2(){
        return this.publisher;
    }
    public void setPublisher2(String publisher){
        this.publisher = publisher;
    }
    public Date getCreatedAt2(){
        return this.createdAt;
    }
    public void setCreatedAt2(Date createdAt){
        this.createdAt = createdAt;
    }
    public String getName2(){
        return this.name;
    }
    public void setName2(String name){
        this.name = name;
    }
    public String getId2(){
        return this.id;
    }
    public void setId2(String id){
        this.id = id;
    }
}
