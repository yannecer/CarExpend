package com.necer.carexpend.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.necer.carexpend.base.baserx.RxManager;

import butterknife.ButterKnife;

/**
 * Created by necer on 2016/11/2.
 */

public abstract class BaseFragment extends Fragment {
    protected View LayoutView;//fragment布局view
    protected Context mContext;
    protected RxManager mRxManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (LayoutView == null) {
            LayoutView = inflater.inflate(getLayoutId(), container, false);
        }
        mContext = getActivity();
        ButterKnife.bind(this, LayoutView);
        mRxManager = new RxManager();
        setDate(savedInstanceState);
        return LayoutView;
    }

    protected abstract int getLayoutId();
    protected abstract void setDate(Bundle savedInstanceState);

}
