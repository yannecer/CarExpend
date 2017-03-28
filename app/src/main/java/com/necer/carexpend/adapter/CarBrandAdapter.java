package com.necer.carexpend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.necer.carexpend.R;
import com.necer.carexpend.bean.CarBrandBean;
import com.necer.carexpend.bean.CarItem;

import java.util.List;

/**
 * Created by necer on 2017/3/9.
 */

public class CarBrandAdapter extends  CarItemAdapter<CarBrandBean>{


    public CarBrandAdapter(Context context, List<CarItem<CarBrandBean>> dataList) {
        super(context, dataList);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_car_brand;
    }

    @Override
    protected RecyclerView.ViewHolder getItemViewHolder(View itemView) {
        return new ItemViewHold(itemView);
    }

    @Override
    protected void onBindData(RecyclerView.ViewHolder holder,CarBrandBean carBrandBean) {

        CarBrandAdapter.ItemViewHold itemViewHold = (CarBrandAdapter.ItemViewHold) holder;
        itemViewHold.tv_name.setText(carBrandBean.getName());
        Glide.with(mContext).load(carBrandBean.getLogo())
                .placeholder(R.mipmap.ic_launcher).into(itemViewHold.iv_icon);
    }

    static class ItemViewHold extends RecyclerView.ViewHolder {
        TextView tv_name;
        ImageView iv_icon;
        public ItemViewHold(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
        }
    }


}
