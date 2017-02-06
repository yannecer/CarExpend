package com.necer.carexpend.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by necer on 2016/11/24.
 */

public abstract class NListAdapter<T> extends BaseAdapter{

    protected Context mContext;
    protected List<T> dataList;
    protected int layoutId;


    public NListAdapter(Context contxt, List<T> dataList, int layoutId) {
        this.mContext = contxt;
        this.dataList = dataList;
        this.layoutId = layoutId;
    }

    public NListAdapter(Context contxt,int layoutId) {
        this.mContext = contxt;
        this.layoutId = layoutId;
        this.dataList = new ArrayList();
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public T getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = View.inflate(mContext, layoutId, null);
        }
        onBindData(view,i, dataList.get(i));
        return view;
    }

    public abstract void onBindData(View view,int position,T t );


    public void setFirstData(List<T> data) {
        dataList.clear();
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    public void setMoreData(List<T> data) {
        dataList.addAll(dataList.size(), data);
        notifyDataSetChanged();
    }

    public void addOne(T t) {
        dataList.add(0, t);
        notifyDataSetChanged();
    }





}
