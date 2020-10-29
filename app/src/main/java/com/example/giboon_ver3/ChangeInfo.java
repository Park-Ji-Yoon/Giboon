package com.example.giboon_ver3;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChangeInfo {
    private String title;
    private String contents;
    private String publisher;
    private String money;
    private Date createdAt;
    public static int count = 0;

    public ChangeInfo(String title, String contents, String publisher, String money, Date createdAt){
        this.title = title;
        this.contents = contents;
        this.publisher = publisher;
        this.money = money;
        this.createdAt = createdAt;
    }

    public Map<String, Object> getPostInfo(){
        Map<String, Object> docData = new HashMap<>();
        docData.put("title",title);
        docData.put("contents",contents);
        docData.put("publisher",publisher);
        docData.put("money",money);
        docData.put("createdAt",createdAt);
        return  docData;
    }

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getContents(){
        return this.contents;
    }
    public void setContents(String contents){
        this.contents = contents;
    }
    public String getPublisher(){
        return this.publisher;
    }
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }
    public String getMoney(){
        return this.money;
    }
    public void setMoney(String money){
        this.money = money;
    }
    public Date getCreatedAt(){
        return this.createdAt;
    }
    public void setCreatedAt(Date createdAt){
        this.createdAt = createdAt;
    }
    public static int getCount(){return count;}
    public void setCount(int count){
        this.count = count;
    }
}
