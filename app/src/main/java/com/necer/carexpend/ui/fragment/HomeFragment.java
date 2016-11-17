package com.necer.carexpend.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.necer.carexpend.MyLog;
import com.necer.carexpend.R;
import com.necer.carexpend.adapter.HomeAdapter;
import com.necer.carexpend.base.BaseFragment;
import com.necer.carexpend.bean.Expend;
import com.necer.carexpend.bean.User;
import com.necer.carexpend.contract.HomeContract;
import com.necer.carexpend.model.HomeFragmentModel;
import com.necer.carexpend.ui.activity.AddExpendActivity;
import com.necer.carexpend.utils.FABUtils;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;


/**
 * Created by necer on 2016/11/1.
 */

public class HomeFragment extends BaseFragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener, HomeContract.View {

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
    private RapidFloatingActionContentLabelList rfaContent;

    private HomeFragmentModel model;

    private User user;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {

        rfaContent = new RapidFloatingActionContentLabelList(mContext);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        rfabHelper=  FABUtils.buildFAB(mContext,rfaContent,rfaLayout,rfaButton);

        initRecyclerView();

        user = BmobUser.getCurrentUser(User.class);
        model = new HomeFragmentModel(user,this);
        model.getDataList();


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

    @Override
    public void setDataList(List<Expend> dataList) {
        MyLog.d("dataList:::"+dataList.get(0).getMoney());
    }

    @Override
    public void startLoading() {
        swipe.setRefreshing(true);
    }

    @Override
    public void endLoading() {
        swipe.setRefreshing(false);
    }

}
