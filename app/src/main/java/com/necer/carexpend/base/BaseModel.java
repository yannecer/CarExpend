package com.necer.carexpend.base;

import com.necer.carexpend.base.baserx.RxManager;

/**
 * Created by necer on 2016/11/17.
 */

public abstract class BaseModel <V extends BaseView>{

    protected RxManager mRxManager;
    protected V mView;

    public BaseModel() {
        mRxManager = new RxManager();
    }

    public void setView(V view) {
        this.mView = view;
    }


    public void onDestroy() {
        mRxManager.clear();
    }


}
