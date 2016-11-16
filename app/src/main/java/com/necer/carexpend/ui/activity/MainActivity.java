package com.necer.carexpend.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.necer.carexpend.MyLog;
import com.necer.carexpend.R;
import com.necer.carexpend.base.BaseActivity;
import com.necer.carexpend.ui.fragment.HomeFragment;

import butterknife.Bind;


public class MainActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.nav_view)
    NavigationView mNavView;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private HomeFragment homeFragment;

    private ImageView headImageView;
    private TextView headTextView;

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
        headImageView = (ImageView) headerView.findViewById(R.id.nav_head_icon);
        headTextView = (TextView) headerView.findViewById(R.id.nav_head_name);

        headImageView.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

                    case R.id.nav_item1:

                        MyLog.d("nav_item1");
                        break;


                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }


    private void navViewToTop() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mDrawerLayout.setFitsSystemWindows(true);
            mDrawerLayout.setClipToPadding(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.nav_head_icon:

                startActivity(new Intent(this, RegisterActivity.class));

                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
    }
}
