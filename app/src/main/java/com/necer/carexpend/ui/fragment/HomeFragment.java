package com.necer.carexpend.ui.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.necer.carexpend.R;
import com.necer.carexpend.adapter.HomeAdapter;
import com.necer.carexpend.application.Constant;
import com.necer.carexpend.base.BaseFragment;
import com.necer.carexpend.bean.Expend;
import com.necer.carexpend.bean.User;
import com.necer.carexpend.contract.HomeContract;
import com.necer.carexpend.model.HomeFragmentModel;
import com.necer.carexpend.service.DelService;
import com.necer.carexpend.service.UpService;
import com.necer.carexpend.ui.activity.AddExpendActivity;
import com.necer.carexpend.utils.FABUtils;
import com.necer.carexpend.utils.SharePrefUtil;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;
import necer.nrecyclerview.MyLog;
import necer.nrecyclerview.NRecyclerView;
import rx.functions.Action1;

import static com.necer.carexpend.service.UpService.EXPEND;


/**
 * Created by necer on 2016/11/1.
 */

public class HomeFragment extends BaseFragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener, HomeContract.View, SwipeRefreshLayout.OnRefreshListener, HomeAdapter.OnDeleteListener,  NRecyclerView.OnLoadMoreListener
{

    @Bind(R.id.label_list_sample_rfal)
    RapidFloatingActionLayout rfaLayout;
    @Bind(R.id.label_list_sample_rfab)
    RapidFloatingActionButton rfaButton;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe;

    @Bind(R.id.nRecyclerview)
    NRecyclerView nRecyclerview;

    @Bind(R.id.tv_noContent)
    TextView tv_noContent;

    private HomeAdapter homeAdapter;
    private RapidFloatingActionHelper rfabHelper;
    private RapidFloatingActionContentLabelList rfaContent;
    private HomeFragmentModel model;
    private User user;
    private int page;
    private boolean isLoading;//正在上传，或者正在删除  防止已经在这个页面显示，用户有重新加载的情况

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

        homeAdapter = new HomeAdapter(mContext);
        homeAdapter.setOnDeleteListener(this);
        nRecyclerview.setAdapter(homeAdapter);
        nRecyclerview.setLoadingMoreEnable(true);
        nRecyclerview.setLoadMoreListener(this);

        user = BmobUser.getCurrentUser(User.class);

        Expend expend = (Expend) SharePrefUtil.getObj(mContext, Constant.WAITFORUPLOAD);
        if (expend != null) {
            Intent intent = new Intent(mContext, UpService.class);
            intent.putExtra(UpService.EXPEND, expend);
            mContext.startService(intent);
        }

        model = new HomeFragmentModel(user, this);
        model.getDataList(page, true, 0);

        mRxManager.on(Constant.LOADING, new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                isLoading = aBoolean;
            }
        });

    }


    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        goAddExpendActivity(item);
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        goAddExpendActivity(item);
    }

    private void goAddExpendActivity(RFACLabelItem item) {
        String label = item.getLabel();
        Intent intent = new Intent(mContext, AddExpendActivity.class);
        intent.putExtra("label", label);
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
    public void onError(String message) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constant.REFRESH:
                    if (data != null) {
                        Expend expend = (Expend) data.getSerializableExtra(Constant.WAITFORUPLOAD);
                        homeAdapter.addItem(expend);
                        nRecyclerview.setSelection(0);

                        tv_noContent.setVisibility(View.GONE);
                        nRecyclerview.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }

    }

    @Override
    public void setFirstList(List<Expend> dataList1) {
        if (dataList1.size() != 0) {
            homeAdapter.replaceData(dataList1);
            tv_noContent.setVisibility(View.GONE);
            nRecyclerview.setVisibility(View.VISIBLE);
        } else {
            tv_noContent.setVisibility(View.VISIBLE);
            nRecyclerview.setVisibility(View.GONE);
        }

    }

    @Override
    public void setMoreList(List<Expend> dataList1) {
        nRecyclerview.stopLoadMore();

        if (dataList1.size() != 0) {
            homeAdapter.addData(dataList1);
        }
    }

    @Override
    public void onErrer(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        tv_noContent.setVisibility(View.VISIBLE);
        nRecyclerview.setVisibility(View.GONE);

    }

    @Override
    public void onLoadMore() {
        MyLog.d("onLoadMore");
        page++;
        model.getDataList(page, false, homeAdapter.getItemCount());
    }

    @Override
    public void onRefresh() {
        if (isLoading) {
            swipe.setRefreshing(false);
        } else {
            page = 0;
            model.getDataList(page, false, 0);
        }

    }

    @Override
    public void onDelete(final Expend expend) {
        new AlertDialog.Builder(mContext).setTitle("确认删除？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        homeAdapter.delItem(expend);
                        if (homeAdapter.getItemCount() == 0) {
                            tv_noContent.setVisibility(View.VISIBLE);
                            nRecyclerview.setVisibility(View.GONE);
                        }

                        Intent intent = new Intent(mContext, DelService.class);
                        intent.putExtra(EXPEND, expend);
                        mContext.startService(intent);

                    }
                })
                .setNegativeButton("取消", null).create().show();

    }
}
