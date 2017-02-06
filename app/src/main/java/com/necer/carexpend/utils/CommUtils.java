package com.necer.carexpend.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.Toast;

import java.text.ParseException;
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


    /**
     * 获取当前版本信息.
     *
     * @param context
     */
    public static String getCurrentVersion(Context context) {
        String versionName = "";
        try {
            final PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }


    /**
     * 判断是否在栈顶
     * @param context
     * @param activity
     * @return
     */
    public static boolean isStackTop(Context context, Activity activity) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String name = manager.getRunningTasks(1).get(0).topActivity.getClassName();
        return TextUtils.equals(activity.getClass().getName(), name);
    }


    /**
     * 连续点击关闭app
     */
    private static long mExitTime;
    public static void doubleCloseActivity(Activity activity) {
        if (CommUtils.isStackTop(activity, activity) && (System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(activity, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            activity.finish();
        }
    }


    /**
     * 年月日时间戳
     *
     * @param dateStr
     * @return
     */
    public static long getTimestamp(String dateStr) {
        if (dateStr.equals("")) {
            return 0;
        }
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        long unixTimestamp = date.getTime() / 1000;
        return unixTimestamp ;

    }


}
