package com.necer.carexpend.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.necer.carexpend.R;
import com.necer.carexpend.application.Constant;
import com.necer.carexpend.base.BaseActivity;
import com.necer.carexpend.bean.User;
import com.necer.carexpend.ui.fragment.HomeFragment;
import com.necer.carexpend.utils.CommUtils;
import com.necer.carexpend.utils.ImageLoaderUtils;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;
import rx.functions.Action1;


public class MainActivity extends BaseActivity {


    @Bind(R.id.nav_view)
    NavigationView mNavView;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private HomeFragment homeFragment;

    private ImageView iv_logo;
    private TextView tv_brand;
    private TextView tv_series;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setData(Bundle savedInstanceState) {

        navViewToTop();
        setUpDrawer();
        initNavigationView();
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_main, homeFragment).commit();


        View headerView = mNavView.getHeaderView(0);//得到侧滑菜单的头部view
        iv_logo = (ImageView) headerView.findViewById(R.id.iv_logo);
        tv_brand = (TextView) headerView.findViewById(R.id.tv_brand);
        tv_series = (TextView) headerView.findViewById(R.id.tv_series);

        initCar();

        mRxManager.on(Constant.CHANGE_CAR, new Action1<Object>() {
            @Override
            public void call(Object o) {
                initCar();
            }
        });


        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CarBrandActivity.class));
                overridePendingTransition(R.anim.activity_open, 0);
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });


    }

    private void initCar() {
        User user = BmobUser.getCurrentUser(User.class);
        ImageLoaderUtils.display(this, iv_logo, user.getCarLogo());
        tv_brand.setText(TextUtils.isEmpty(user.getCarBrand()) ? "选择我的汽车" : user.getCarBrand());
        tv_series.setText(TextUtils.isEmpty(user.getCarSeries()) ? "" : user.getCarSeries());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            CommUtils.doubleCloseActivity(this);
        }
    }

    private void setUpDrawer() {

        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initNavigationView() {
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_out:
                        goOut();
                        break;
                    case R.id.nav_about:
                        startActivity(new Intent(MainActivity.this,AboutActivity.class));
                        overridePendingTransition(R.anim.activity_open, 0);
                        break;
                    case R.id.nav_kind:
                        Toast.makeText(MainActivity.this, "请等待。。", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_time:
                        Toast.makeText(MainActivity.this, "请等待。。", Toast.LENGTH_SHORT).show();
                        break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void goOut() {

       // AlertDialog
        new AlertDialog.Builder(this).setTitle("确认退出登录？")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BmobUser.logOut();   //清除缓存用户对象
                        finish();
                    }
                }).create().show();



    }


    private void navViewToTop() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mDrawerLayout.setFitsSystemWindows(true);
            mDrawerLayout.setClipToPadding(false);
        }
    }



}
