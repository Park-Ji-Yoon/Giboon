package com.example.giboon_ver3;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CheckedInfo {
    private String publisher;
    String month;
    private boolean[] checked = new boolean[30];

    public CheckedInfo(String publisher){
        this.publisher = publisher;
        setDate();
        this.month = getDate();

    }

    public Map<String, Object> getPostInfo(){
        Map<String, Object> docData = new HashMap<>();
        docData.put("publisher",publisher);
        docData.put("checked", month);
        return  docData;
    }

    public String getPublisher(){
        return this.publisher;
    }
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }
    public void setDate(){
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        this.month = simpleDateFormat.format(mDate);
    }
    public String getDate(){
        return this.month;
    }
}
