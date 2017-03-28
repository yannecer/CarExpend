package com.necer.carexpend.bean;

/**
 * Created by necer on 2017/3/9.
 */

public class CarItem<T> {


    private boolean isTitle;
    private String title;
    private T t;


    public CarItem(boolean isTitle, String title, T t) {
        this.isTitle = isTitle;
        this.title = title;
        this.t = t;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
