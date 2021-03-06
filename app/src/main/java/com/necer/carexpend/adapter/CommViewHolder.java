package com.necer.carexpend.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by necer on 2016/8/12.
 */
public class CommViewHolder extends RecyclerView.ViewHolder{

    private SparseArray<View> mViews;
    private View mItemView;

    public CommViewHolder(View itemView, ViewGroup parent) {
        super(itemView);
        mItemView = itemView;//这个是item的布局文件，layout
        mViews = new SparseArray<>();
    }


    public static CommViewHolder getViewHolder(Context context, ViewGroup parent, int layoutId) {

        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        CommViewHolder commonViewHolder = new CommViewHolder(itemView, parent);
        return commonViewHolder;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mItemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T)view;
    }

}
