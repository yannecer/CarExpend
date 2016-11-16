package com.necer.carexpend.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by necer on 2016/11/16.
 */

public class CommUtils {



    /**
     * 得到当前时间的毫秒值转成时间
     *
     * @return
     */
    public static String getNowDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }






}
