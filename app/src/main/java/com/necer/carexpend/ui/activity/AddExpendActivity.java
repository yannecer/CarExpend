package com.necer.carexpend.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.necer.carexpend.MyLog;
import com.necer.carexpend.R;
import com.necer.carexpend.adapter.NinePicturesAdapter;
import com.necer.carexpend.base.BaseActivity;
import com.necer.carexpend.bean.User;
import com.necer.carexpend.model.AddExpandModel;
import com.necer.carexpend.utils.CommUtils;
import com.necer.carexpend.utils.ImageLoaderUtils;
import com.necer.carexpend.view.NoScrollGridView;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;

/**
 * Created by necer on 2016/11/2.
 */

public class AddExpendActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.gridview)
    NoScrollGridView gridview;
    @Bind(R.id.et_money)
    EditText et_money;
    @Bind(R.id.et_describe)
    EditText et_describe;


    private Menu menu;

    private NinePicturesAdapter ninePicturesAdapter;

    private AddExpandModel model;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_addexpend;
    }

    @Override
    protected void setData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        model = new AddExpandModel();

        ninePicturesAdapter = new NinePicturesAdapter(this, 9, new NinePicturesAdapter.OnClickAddListener() {
            @Override
            public void onClickAdd(int positin) {
                choosePhoto();
            }
        });
        gridview.setAdapter(ninePicturesAdapter);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        if (item.getItemId() == R.id.menu_time) {
            MyLog.d("时间");


        }
        if (item.getItemId() == R.id.menu_submit) {
            submit();

        }

        /*else {



           *//* ;
            for (int i = 0; i < data.size(); i++) {
                MyLog.d("data::"+data.get(i));
            }*//*
        }*/


        return true;
    }

    private void submit() {

        //progressDialog.show();

        String money = et_money.getText().toString();
        String describe = et_describe.getText().toString();
        String date = menu.getItem(0).getTitle().toString();



        List<String> imageList = ninePicturesAdapter.getData();
        User user = BmobUser.getCurrentUser(User.class);



        for (int i = 0; i < imageList.size(); i++) {
            if (imageList.get(i).equals("")) {
                imageList.remove(i);
            }
        }


        if (money.equals("")) {
            Snackbar.make(et_money,"请输入money！",Snackbar.LENGTH_SHORT).show();
            return;
        }
        MyLog.d("user::"+user);

        model.submitExpand(user, money, date, describe, imageList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_submit, menu);

        menu.getItem(0).setTitle(CommUtils.getNowDate());
        return true;
    }


    /**
     * 开启图片选择器
     */
    private void choosePhoto() {
        ImgSelConfig config = new ImgSelConfig.Builder(loader)
                // 是否多选
                .multiSelect(true)
                // 确定按钮背景色
                .btnBgColor(Color.TRANSPARENT)
                .titleBgColor(ContextCompat.getColor(this, R.color.colorPrimary))
                // 使用沉浸式状态栏
                .statusBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                // 返回图标ResId
                .backResId(R.mipmap.ic_arrow_back)
                .title("图片")
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(9 - ninePicturesAdapter.getPhotoCount())
                .build();
        ImgSelActivity.startActivity(this, config, REQUEST_CODE);
    }

    private int REQUEST_CODE = 120;
    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            ImageLoaderUtils.display(context, imageView, path);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);

            if (ninePicturesAdapter != null) {
                ninePicturesAdapter.addAll(pathList);
            }
        }
    }

}
