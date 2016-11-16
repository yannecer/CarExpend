package com.necer.carexpend.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.necer.carexpend.R;
import com.necer.carexpend.base.BaseActivity;
import com.necer.carexpend.bean.User;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by necer on 2016/11/16.
 */
public class RegisterActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.et_mobileNumber)
    EditText et_mobileNumber;
    @Bind(R.id.et_passWord)
    EditText et_passWord;
    @Bind(R.id.et_userName)
    EditText et_userName;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void setData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;


    }

    @OnClick(value = {R.id.iv_icon, R.id.bt_register})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bt_register:


                String mobileNumber = et_mobileNumber.getText().toString();
                String password = et_passWord.getText().toString();
                String userName = et_userName.getText().toString();

                String regExp = "^1[3,4,5,7,8]\\d{9}$";
                if ("".equals(mobileNumber) || !mobileNumber.matches(regExp)) {
                    Snackbar.make(et_mobileNumber, "请输入正确的手机号！", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if ("".equals(password)) {
                    Snackbar.make(et_mobileNumber, "密码不能为空！", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if ("".equals(userName)) {
                    Snackbar.make(et_mobileNumber, "昵称不能为空！", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                register(mobileNumber, password, userName);

                break;
        }

    }

    private void register(String mobileNumber, String password, String userName) {
        progressDialog.show();

        User user = new User();
        user.setUsername(userName);
        user.setMobilePhoneNumber(mobileNumber);
        user.setPassword(password);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                progressDialog.dismiss();
                if (e == null) {
                    Snackbar.make(et_mobileNumber, "注册成功！", Snackbar.LENGTH_SHORT).show();
                    RegisterActivity.this.finish();
                } else {
                    Snackbar.make(et_mobileNumber, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }

            }
        });

    }


}
