package com.necer.carexpend.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.necer.carexpend.R;
import com.necer.carexpend.adapter.CarItemAdapter;
import com.necer.carexpend.adapter.CarListAdapter;
import com.necer.carexpend.application.Constant;
import com.necer.carexpend.base.BaseActivity;
import com.necer.carexpend.bean.CarItem;
import com.necer.carexpend.bean.CarListBean;
import com.necer.carexpend.bean.User;
import com.necer.carexpend.contract.CarListContract;
import com.necer.carexpend.model.CarListModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by necer on 2017/3/9.
 */

public class CarListActivity extends BaseActivity<CarListModel> implements CarListContract.View<CarListBean>, SwipeRefreshLayout.OnRefreshListener, CarItemAdapter.OnItemClickListener {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_carlist;
    }

    private String id;
    private String brandName;
    private String brandLogo;




    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.recycler)
    RecyclerView recycler;

    @Bind(R.id.tv_noContent)
    TextView tv_noContent;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe;

    private List<CarItem<CarListBean>> dataList;
    private CarListAdapter carListAdapter;




    @Override
    protected void setData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipe.setOnRefreshListener(this);

        id = getIntent().getStringExtra("id");
        brandName = getIntent().getStringExtra("brand");
        brandLogo = getIntent().getStringExtra("logo");
        mModel.setView(this);
        mModel.getCarList(Constant.APPKEY,id);

        dataList = new ArrayList<>();
        carListAdapter = new CarListAdapter(this, dataList);

        recycler.setLayoutManager(new LinearLayoutManager(this));
     //   recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recycler.setAdapter(carListAdapter);
        carListAdapter.setOnItemClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void setCarList(List<CarItem<CarListBean>> carList) {
        dataList.clear();
        dataList.addAll(carList);
        carListAdapter.notifyDataSetChanged();

        tv_noContent.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void startLoading() {
        swipe.setRefreshing(true);
    }

    @Override
    public void endLoading() {
        swipe.setRefreshing(false);
    }

    @Override
    public void onError(String message) {
        tv_noContent.setText(message);
        tv_noContent.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        mModel.getCarList(Constant.APPKEY,id);
    }

    @Override
    public void onItemClick(int position) {
        progressDialog.show();
        String fullname = dataList.get(position).getT().getFullname();
        User currentUser = BmobUser.getCurrentUser(User.class);


        User user = new User();
        user.setCarLogo(brandLogo);
        user.setCarSeries(fullname);
        user.setCarBrand(brandName);

        user.update(currentUser.getObjectId(),new UpdateListener() {
            @Override
            public void done(BmobException e) {
                progressDialog.dismiss();
                if(e==null){

                    mRxManager.post(Constant.CHANGE_CAR, "");

                    Toast.makeText(CarListActivity.this,"更新成功！",Toast.LENGTH_SHORT).show();
                    CarListActivity.this.finish();
                }else{
                    Snackbar.make(toolbar,e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }
        });


    }
}
