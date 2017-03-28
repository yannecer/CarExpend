package com.necer.carexpend.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.necer.carexpend.R;
import com.necer.carexpend.application.Constant;
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
    @Bind(R.id.et_passWord1)
    EditText et_passWord1;


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
                String passWord1 = et_passWord1.getText().toString();

                String regExp = "^1[3,4,5,7,8]\\d{9}$";
                if ("".equals(mobileNumber) || !mobileNumber.matches(regExp)) {
                    Snackbar.make(et_mobileNumber, "请输入正确的手机号！", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if ("".equals(password)) {
                    Snackbar.make(et_mobileNumber, "密码不能为空！", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(passWord1)) {
                    Snackbar.make(et_mobileNumber, "两次密码不一致！", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                register(mobileNumber, password);

                break;
           /* case R.id.tv_goLogin:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;*/
        }

    }

    private void register(String mobileNumber, String password) {
        progressDialog.show();
        User user = new User();
        user.setMobilePhoneNumber(mobileNumber);
        user.setPassword(password);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                progressDialog.dismiss();
                if (e == null) {
                    mRxManager.post(Constant.LOGIN_SUCCESS, null);
                    Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    RegisterActivity.this.finish();
                } else {
                    Snackbar.make(et_mobileNumber, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }


}
