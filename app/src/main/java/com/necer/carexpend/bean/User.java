package com.necer.carexpend.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by necer on 2016/11/16.
 */

public class User extends BmobUser {


    private String carBrand;//品牌
    private String carSeries;//车系
    private String carLogo;//车图片

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarSeries() {
        return carSeries;
    }

    public void setCarSeries(String carSeries) {
        this.carSeries = carSeries;
    }

    public String getCarLogo() {
        return carLogo;
    }

    public void setCarLogo(String carLogo) {
        this.carLogo = carLogo;
    }
}
