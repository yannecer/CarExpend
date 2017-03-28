package com.necer.carexpend.bean;

import java.util.List;

/**
 * Created by necer on 2017/3/9.
 */

public class CarItemBean {


    private String depth;
    private String id;
    private String initial;
    private String name;
    private String parentid;
    private List<CarListBean> carlist;


    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public List<CarListBean> getCarlist() {
        return carlist;
    }

    public void setCarlist(List<CarListBean> carlist) {
        this.carlist = carlist;
    }
}
