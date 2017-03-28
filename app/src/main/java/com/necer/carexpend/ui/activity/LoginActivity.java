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
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by necer on 2016/11/16.
 */

public class LoginActivity extends BaseActivity{

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_mobileNumber)
    EditText et_mobileNumber;
    @Bind(R.id.et_passWord)
    EditText et_passWord;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void setData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @OnClick(value = {R.id.bt_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                String mobileNumber = et_mobileNumber.getText().toString();
                String passWord = et_passWord.getText().toString();

                String regExp = "^1[3,4,5,7,8]\\d{9}$";
                if ("".equals(mobileNumber) || !mobileNumber.matches(regExp)) {
                    Snackbar.make(et_mobileNumber, "请输入正确的手机号！", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if ("".equals(passWord)) {
                    Snackbar.make(et_mobileNumber, "密码不能为空！", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                login(mobileNumber,passWord);
                break;
           /* case R.id.tv_goRegister:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;*/
        }
}

    private void login(String mobileNumber, String passWord) {
        progressDialog.show();
        BmobUser.loginByAccount(mobileNumber, passWord, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                progressDialog.dismiss();
                if (e == null) {
                    mRxManager.post(Constant.LOGIN_SUCCESS,null );
                    Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    LoginActivity.this.finish();
                } else {
                    Snackbar.make(et_mobileNumber,e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

}
