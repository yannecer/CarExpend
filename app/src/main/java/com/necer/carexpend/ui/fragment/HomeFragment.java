package com.necer.carexpend.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.necer.carexpend.MyLog;
import com.necer.carexpend.R;
import com.necer.carexpend.adapter.HomeAdapter;
import com.necer.carexpend.application.Constant;
import com.necer.carexpend.base.BaseFragment;
import com.necer.carexpend.bean.Expend;
import com.necer.carexpend.bean.User;
import com.necer.carexpend.contract.HomeContract;
import com.necer.carexpend.model.HomeFragmentModel;
import com.necer.carexpend.service.UpService;
import com.necer.carexpend.ui.activity.AddExpendActivity;
import com.necer.carexpend.utils.FABUtils;
import com.necer.carexpend.utils.MessageEvent;
import com.necer.carexpend.utils.SharePrefUtil;
import com.necer.carexpend.view.NListView;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;


/**
 * Created by necer on 2016/11/1.
 */

public class HomeFragment extends BaseFragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener, HomeContract.View, SwipeRefreshLayout.OnRefreshListener, HomeAdapter.OnDeleteListener, NListView.OnLoadMoreListener {

    @Bind(R.id.label_list_sample_rfal)
    RapidFloatingActionLayout rfaLayout;
    @Bind(R.id.label_list_sample_rfab)
    RapidFloatingActionButton rfaButton;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe;
    @Bind(R.id.nlistview)
    NListView nlistview;


    private HomeAdapter homeAdapter;


    private RapidFloatingActionHelper rfabHelper;
    private RapidFloatingActionContentLabelList rfaContent;

    private HomeFragmentModel model;

    private User user;
    private int page;
    private boolean isUpLoading;//正在上传  防止已经在这个页面显示，用户有重新加载的情况

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {

        rfaContent = new RapidFloatingActionContentLabelList(mContext);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        rfabHelper = FABUtils.buildFAB(mContext, rfaContent, rfaLayout, rfaButton);
        swipe.setOnRefreshListener(this);

        homeAdapter = new HomeAdapter(mContext, R.layout.item_home);
        homeAdapter.setOnDeleteListener(this);
        nlistview.setAdapter(homeAdapter);
        nlistview.setOnLoadMoreListener(this);

        user = BmobUser.getCurrentUser(User.class);

        Expend expend = (Expend) SharePrefUtil.getObj(mContext, Constant.WAITFORUPLOAD);
        if (expend != null) {
            Intent intent = new Intent(mContext, UpService.class);
            intent.putExtra(UpService.EXPEND, expend);
            mContext.startService(intent);
        }

        model = new HomeFragmentModel(user, this);
        model.getDataList(page, true, 0);

        EventBus.getDefault().register(this);

    }


    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        goAddExpendActivity(position, item);
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        goAddExpendActivity(position, item);
    }

    private void goAddExpendActivity(int position, RFACLabelItem item) {
        String label = item.getLabel();
        Intent intent = new Intent(mContext, AddExpendActivity.class);
        intent.putExtra("label", label);
        intent.putExtra("type", position);
        startActivityForResult(intent, Constant.REFRESH);
        rfabHelper.collapseContent();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constant.REFRESH:
                    if (data != null) {
                        Expend expend = (Expend) data.getSerializableExtra(Constant.WAITFORUPLOAD);
                        homeAdapter.addOne(expend);
                        nlistview.setSelection(0);
                    }

                    break;

            }
        }

    }

    @Override
    public void setFirstList(List<Expend> dataList1) {
        homeAdapter.setFirstData(dataList1);
    }

    @Override
    public void setMoreList(List<Expend> dataList1) {
        nlistview.stopLoadMore();
        homeAdapter.setMoreData(dataList1);
    }

    @Override
    public void onLoadMore() {
        page++;
        model.getDataList(page, false, homeAdapter.getCount());
    }

    @Override
    public void onRefresh() {
        if (isUpLoading) {
            swipe.setRefreshing(false);
        } else {
            page = 0;
            model.getDataList(page, false, 0);
        }

    }

    @Override
    public void onDelete(Expend expend) {
        MyLog.d(":::" + expend.getObjectId());

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        isUpLoading = event.isEnable();
        MyLog.d("isUpLoading::" + isUpLoading);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
