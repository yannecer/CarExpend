package com.necer.carexpend.adapter;

import android.content.Context;
import android.widget.TextView;

import com.necer.carexpend.R;

import java.util.List;

/**
 * Created by necer on 2016/11/1.
 */

public class HomeAdapter extends CommAdapter<String> {
    public HomeAdapter(Context context, List<String> dataList, int layoutId) {
        super(context, dataList, layoutId);

    }

    @Override
    public void onBindData(CommViewHolder holder, String s, int position) {

        TextView tv_expend_option = holder.getView(R.id.tv_expend_option);
        //tv_expend_option.setText(s);



    }
}
