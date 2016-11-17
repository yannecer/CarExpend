package com.necer.carexpend.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by necer on 2016/11/16.
 * 花费
 */

public class Expend extends BmobObject {

    private User user;
    private double money;
    private String describe;
    private String date;
    private int type;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String time) {
        this.date = time;
    }

    private List<String> imageUrl;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }
}
