package com.necer.carexpend.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by necer on 2016/11/17.
 */

public class WatingDialog {


    private static ProgressDialog progressDialog;

    private static ProgressDialog getInstance(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("请稍后...");
        }
        return progressDialog;
    }

    public static void show(Activity activity) {

        if (!activity.isFinishing()) {
            getInstance(activity).show();

        }

    }

    public static void show(Activity activity, String message) {
        getInstance(activity).setMessage(message);
        show(activity);
    }

    public static void dismiss(Context context) {
        getInstance(context).dismiss();
    }




}
