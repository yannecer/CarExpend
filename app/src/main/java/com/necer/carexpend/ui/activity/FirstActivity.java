package com.necer.carexpend.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.necer.carexpend.R;
import com.necer.carexpend.application.Constant;
import com.necer.carexpend.base.BaseActivity;

import rx.functions.Action1;

/**
 * Created by necer on 2017/3/10.
 */

public class FirstActivity extends BaseActivity{
    @Override
    protected int getLayoutId() {
        return R.layout.activit_first;
    }

    @Override
    protected void setData(Bundle savedInstanceState) {
        mRxManager.on(Constant.LOGIN_SUCCESS, new Action1<Object>() {
            @Override
            public void call(Object o) {
                FirstActivity.this.finish();
            }
        });
    }


    public void login(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }


    public void register(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
