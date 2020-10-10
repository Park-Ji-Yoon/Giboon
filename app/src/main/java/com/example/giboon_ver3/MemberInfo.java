package com.example.giboon_ver3;

public class MemberInfo {
    // 변수 선언 (이름, 폰
    private String name;
    private String phone;

    // 생성자 (일단 이름과 전화번호만)
    public MemberInfo(String name, String phone){
        this.name = name;
        this.phone = phone;
    }

    // setter, getter
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getPhone(){
        return this.phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
}
