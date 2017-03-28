package com.necer.carexpend.bean;

import java.util.List;

/**
 * Created by necer on 2017/3/9.
 */

public class CarListBean {
    /**
     * depth : 3
     * fullname : 奥迪A3
     * id : 220
     * initial : A
     * logo : http://api.jisuapi.com/car/static/images/logo/300/220.jpg
     * name : A3
     * parentid : 219
     * salestate : 在销
     */

    private String depth;
    private String fullname;
    private String id;
    private String initial;
    private String logo;
    private String name;
    private String parentid;
    private String salestate;
    private List list;

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getSalestate() {
        return salestate;
    }

    public void setSalestate(String salestate) {
        this.salestate = salestate;
    }
}
