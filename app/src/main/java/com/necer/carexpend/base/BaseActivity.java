package com.necer.carexpend.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.necer.carexpend.R;

import butterknife.ButterKnife;

/**
 * Created by necer on 2016/11/2.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();
        setTheme(R.style.AppTheme_NoActionBar);
        mContext = this;

        setContentView(getLayoutId());
        ButterKnife.bind(this);
        setData(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("请稍后");
        progressDialog.setCanceledOnTouchOutside(false);

    }

    protected abstract int getLayoutId();
    protected abstract void setData(Bundle savedInstanceState);



    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }


}
