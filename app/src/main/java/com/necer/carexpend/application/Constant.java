package com.necer.carexpend.application;

import com.necer.carexpend.R;

/**
 * Created by necer on 2016/11/17.
 */

public class Constant {


    public final static String BMOBAPPKEY = "9416b1587b7bf3347599bcfc848a62f9";
    public final static String[] EXPENDITEMS = new String[]{"维修", "保险", "保养", "停车费", "过路费", "罚单", "洗车","加油"};
    public final static int[] EXPENDICON = new int[]{R.mipmap.car_fix, R.mipmap.car_insurance, R.mipmap.car_maintenance, R.mipmap.car_parking_fee, R.mipmap.car_road_fee, R.mipmap.car_violation, R.mipmap.car_washing,R.mipmap.ic_launcher};
    public final static String[] EXPENDCOLOR = new String[]{"#16B180", "#B1AF16", "#B1AF16", "#01519B", "#019B79", "#CE0505", "#057BCE", "#057BCE"};
    public final static int[] EXPENDTYPE = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 10};
    public final static int REFRESH = 100;
    public final static String WAITFORUPLOAD = "expend";


    //更换车辆
    public final static String CHANGE_CAR = "change_car";

    //登录成功
    public final static String LOGIN_SUCCESS = "login_success";

    //正在上传或删除
    public final static String LOADING = "loading";


    /**
     * 极速数据
     * https://www.jisuapi.com/api/car/
     */
    public final static String APPKEY = "73376a04829963d7";
    public final static String BASE_URL = "http://api.jisuapi.com/car/";






}
