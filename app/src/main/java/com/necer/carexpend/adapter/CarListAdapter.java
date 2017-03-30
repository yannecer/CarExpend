package com.necer.carexpend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.necer.carexpend.R;
import com.necer.carexpend.bean.CarItem;
import com.necer.carexpend.bean.CarListBean;

import java.util.List;

/**
 * Created by necer on 2017/3/9.
 */

public class CarListAdapter extends CarItemAdapter<CarListBean>{
    public CarListAdapter(Context context, List<CarItem<CarListBean>> dataList) {
        super(context, dataList);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_car_list;
    }

    @Override
    protected RecyclerView.ViewHolder getItemViewHolder(View itemView) {
        return new ItemViewHolder(itemView);
    }

    @Override
    protected void onBindData(RecyclerView.ViewHolder holder, CarListBean carListBean) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        itemViewHolder.tv_name.setText(carListBean.getFullname());
        itemViewHolder.tv_desc.setText(carListBean.getSalestate());
        Glide.with(mContext).load(carListBean.getLogo())
                .fitCenter().into(itemViewHolder.iv_pic);

    }



    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_desc;
        ImageView iv_pic;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
            iv_pic = (ImageView) itemView.findViewById(R.id.iv_pic);

        }
    }
}
