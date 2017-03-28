package com.necer.carexpend.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.necer.carexpend.R;
import com.necer.carexpend.base.BaseActivity;
import com.necer.carexpend.bean.User;
import com.necer.carexpend.utils.CommUtils;
import com.necer.carexpend.utils.SharePrefUtil;

import cn.bmob.v3.BmobUser;

/**
 * Created by necer on 2017/2/4.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setData(Bundle savedInstanceState) {

        handler.sendEmptyMessageDelayed(100, 2000);
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String nowVersionName = CommUtils.getCurrentVersion(mContext);
            String lastVersionName = SharePrefUtil.getString(mContext, "lastVersionName", "");
            if (TextUtils.equals(nowVersionName, lastVersionName)) {
                //进入引导页
            } else {
                //进入主页
                User user = BmobUser.getCurrentUser(User.class);
                Intent intent = new Intent();
                intent.setClass(mContext, user == null ? FirstActivity.class : MainActivity.class);
                startActivity(intent);
            }
            finish();
        }
    };
}
