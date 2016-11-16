package com.necer.carexpend.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.necer.carexpend.R;
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


    @OnClick(value = {R.id.bt_login,R.id.tv_goRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:

                String mobileNumber = et_mobileNumber.getText().toString();
                String passWord = et_passWord.getText().toString();


                login(mobileNumber,passWord);




                break;
        }
}

    private void login(String mobileNumber, String passWord) {

       /* User user = new User();
        user.setMobilePhoneNumber(mobileNumber);
        user.setPassword(passWord);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {

            }
        });*/

        BmobUser.loginByAccount(mobileNumber, passWord, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Snackbar.make(et_mobileNumber, "登录成功！", Snackbar.LENGTH_SHORT).show();
                    LoginActivity.this.finish();
                } else {
                    Snackbar.make(et_mobileNumber,e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }

            }
        });

    }


}
