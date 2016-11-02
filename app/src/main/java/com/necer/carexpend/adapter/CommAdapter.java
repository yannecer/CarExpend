package com.necer.carexpend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by necer on 2016/11/2.
 */

public abstract class CommAdapter<T> extends RecyclerView.Adapter<CommViewHolder>{

    public List<T> dataList;
    public Context mContext;
    public int layoutId;


    public CommAdapter(Context context, List<T> dataList, int layoutId) {
        this.mContext = context;
        this.dataList = dataList;
        this.layoutId = layoutId;

    }



    @Override
    public CommViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommViewHolder viewHolder = CommViewHolder.getViewHolder(mContext, parent, layoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommViewHolder holder, int position) {
        T t = dataList.get(position);
        onBindData(holder, t, position);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public abstract void onBindData(CommViewHolder holder, T t,int position);
}
