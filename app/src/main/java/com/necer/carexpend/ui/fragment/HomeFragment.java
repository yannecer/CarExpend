package com.necer.carexpend.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.necer.carexpend.R;
import com.necer.carexpend.adapter.HomeAdapter;
import com.necer.carexpend.base.BaseFragment;
import com.necer.carexpend.ui.activity.AddExpendActivity;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * Created by necer on 2016/11/1.
 */

public class HomeFragment extends BaseFragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {

    @Bind(R.id.label_list_sample_rfal)
    RapidFloatingActionLayout rfaLayout;
    @Bind(R.id.label_list_sample_rfab)
    RapidFloatingActionButton rfaButton;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe;
    @Bind(R.id.recyclerView)
    LuRecyclerView recyclerView;


    private List<String> dataList;
    private HomeAdapter homeAdapter;

    private LuRecyclerViewAdapter mLRecyclerViewAdapter;

    private RapidFloatingActionHelper rfabHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {

        initFAB();
        initRecyclerView();


    }

    private void initRecyclerView() {
        dataList = new ArrayList<>();
        for (int i = 0; i <50 ; i++) {
            dataList.add("ssss");
        }
        homeAdapter = new HomeAdapter(mContext,dataList, R.layout.item_home);

        mLRecyclerViewAdapter = new LuRecyclerViewAdapter(homeAdapter);
        recyclerView.setAdapter(mLRecyclerViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

    }

    private void initFAB() {
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(mContext);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("维修费用")
                .setResId(R.mipmap.car_fix)
                .setLabelColor(Color.parseColor("#16B180"))
                .setWrapper(1)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("保险")
                .setResId(R.mipmap.car_insurance)
                .setLabelColor(Color.parseColor("#B1AF16"))
                .setWrapper(2)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("保养")
                .setResId(R.mipmap.car_maintenance)
                .setLabelColor(Color.parseColor("#B1AF16"))
                .setWrapper(3)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("停车费")
                .setResId(R.mipmap.car_parking_fee)
                .setLabelColor(Color.parseColor("#019B79"))
                .setWrapper(3)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("过路费")
                .setResId(R.mipmap.car_road_fee)
                .setLabelColor(Color.parseColor("#019B79"))
                .setWrapper(3)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("违章罚单")
                .setResId(R.mipmap.car_violation)
                .setLabelColor(Color.parseColor("#CE0505"))
                .setWrapper(3)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("洗车")
                .setResId(R.mipmap.car_washing)
                .setLabelColor(Color.parseColor("#057BCE"))
                .setWrapper(3)
        );
        rfaContent
                .setItems(items)
                .setIconShadowRadius(ABTextUtil.dip2px(mContext, 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(ABTextUtil.dip2px(mContext, 5))
        ;

        rfabHelper = new RapidFloatingActionHelper(
                mContext,
                rfaLayout,
                rfaButton,
                rfaContent).build();
    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        goAddExpendActivity(position);
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        goAddExpendActivity(position);
    }


    private void  goAddExpendActivity(int position) {
        Intent intent = new Intent(mContext, AddExpendActivity.class);
        startActivity(intent);
        rfabHelper.collapseContent();
    }

}
