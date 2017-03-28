package com.necer.carexpend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.necer.carexpend.R;
import com.necer.carexpend.bean.CarItem;

import java.util.List;

/**
 * Created by necer on 2017/3/9.
 */

public abstract class CarItemAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater layoutInflater;
    private List<CarItem<T>> dataList;
    protected Context mContext;

    private final static int TITLE = 100;
    private final static int ITEM = 200;

    public CarItemAdapter( Context context,List<CarItem<T>> dataList ) {
        layoutInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == TITLE) {
            View titleView = layoutInflater.inflate(R.layout.item_cat_title, parent,false);
            TitleViewHold titleViewHold = new TitleViewHold(titleView);
            return titleViewHold;
        } else {
            View itemView = layoutInflater.inflate(getItemLayoutId(), parent, false);
            final RecyclerView.ViewHolder holder = getItemViewHolder(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(holder.getLayoutPosition());
                    }
                }
            });
            return holder;
        }

    }

    protected abstract int getItemLayoutId();

    protected abstract RecyclerView.ViewHolder getItemViewHolder(View itemView);

    protected abstract void onBindData(RecyclerView.ViewHolder holder,T t);


    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position).isTitle()) {
            return TITLE;
        }
        return ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        int itemViewType = holder.getItemViewType();
        if (itemViewType == TITLE) {
            TitleViewHold titleViewHold = (TitleViewHold) holder;
            titleViewHold.tv_title.setText(dataList.get(position).getTitle());
        } else {
            CarItem<T> tCarItem = dataList.get(position);
            onBindData( holder,tCarItem.getT());

        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    static class TitleViewHold extends RecyclerView.ViewHolder {

        TextView tv_title;
        public TitleViewHold(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

}
