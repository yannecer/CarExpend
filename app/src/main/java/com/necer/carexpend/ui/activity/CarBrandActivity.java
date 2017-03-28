package com.necer.carexpend.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gjiazhe.wavesidebar.WaveSideBar;
import com.necer.carexpend.R;
import com.necer.carexpend.adapter.CarBrandAdapter;
import com.necer.carexpend.application.Constant;
import com.necer.carexpend.base.BaseActivity;
import com.necer.carexpend.bean.CarBrandBean;
import com.necer.carexpend.bean.CarItem;
import com.necer.carexpend.contract.CarBrandContract;
import com.necer.carexpend.model.CarBrandContractModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * Created by necer on 2017/3/7.
 */

public class CarBrandActivity extends BaseActivity<CarBrandContractModel> implements CarBrandContract.View<CarBrandBean>, CarBrandAdapter.OnItemClickListener, WaveSideBar.OnSelectIndexItemListener, SwipeRefreshLayout.OnRefreshListener {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_car_brand;
    }

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.recycler)
    RecyclerView recycler;

    @Bind(R.id.side_bar)
    WaveSideBar sideBar;

    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe;

    @Bind(R.id.tv_noContent)
    TextView tv_noContent;


    private CarBrandAdapter carBrandAdapter;
    private List<CarItem<CarBrandBean>> dataList;

    @Override
    protected void setData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mModel.setView(this);
        mModel.getCarBrandList(Constant.APPKEY);

        swipe.setOnRefreshListener(this);

        dataList = new ArrayList<>();
        carBrandAdapter = new CarBrandAdapter(this, dataList);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(carBrandAdapter);
        carBrandAdapter.setOnItemClickListener(this);

        sideBar.setOnSelectIndexItemListener(this);

        mRxManager.on(Constant.CHANGE_CAR, new Action1<Object>() {
            @Override
            public void call(Object o) {
                CarBrandActivity.this.finish();
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

    @Override
    public void setCarBrandList(List<CarItem<CarBrandBean>> carBrandItems) {
        dataList.clear();
        dataList.addAll(carBrandItems);

        carBrandAdapter.notifyDataSetChanged();

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
    public void onItemClick(int position) {

        Intent intent = new Intent(this, CarListActivity.class);
        intent.putExtra("id", dataList.get(position).getT().getId());
        intent.putExtra("brand", dataList.get(position).getT().getName());
        intent.putExtra("logo", dataList.get(position).getT().getLogo());
        startActivity(intent);
    }

    @Override
    public void onSelectIndexItem(String index) {
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getTitle().equals(index)) {
                ((LinearLayoutManager) recycler.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                return;
            }
        }
    }

    @Override
    public void onRefresh() {
        mModel.getCarBrandList(Constant.APPKEY);
    }


}
