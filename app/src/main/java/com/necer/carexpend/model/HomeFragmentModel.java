package com.necer.carexpend.model;

import com.necer.carexpend.bean.Expend;
import com.necer.carexpend.bean.User;
import com.necer.carexpend.contract.HomeContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by necer on 2016/11/16.
 */

public class HomeFragmentModel implements HomeContract.Model{


    private HomeContract.View mView;
    private User user;


    public HomeFragmentModel(User user,HomeContract.View view) {
        this.user = user;
        this.mView = view;
    }

    @Override
    public void getDataList(final int page,boolean isShowLoading,int dataSize) {
        if (isShowLoading) {
            mView.startLoading();
        }
        BmobQuery<Expend> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("user", user);
        bmobQuery.order("-createdAt");
        bmobQuery.setLimit(10);
        bmobQuery.setSkip(dataSize);
        bmobQuery.findObjects(new FindListener<Expend>() {
            @Override
            public void done(List<Expend> list, BmobException e) {
                if (e == null) {
                    if (page == 0) {
                        mView.setFirstList(list);
                    } else {
                        mView.setMoreList(list);
                    }
                }
                mView.endLoading();
            }
        });

    }


}
