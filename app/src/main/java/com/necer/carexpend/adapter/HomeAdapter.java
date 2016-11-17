package com.necer.carexpend.adapter;

import android.content.Context;
import android.widget.TextView;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.necer.carexpend.R;
import com.necer.carexpend.bean.Expend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by necer on 2016/11/1.
 */

public class HomeAdapter extends CommAdapter<Expend> {


    private Context mContext;

    public HomeAdapter(Context context, List<Expend> dataList, int layoutId) {
        super(context, dataList, layoutId);
        this.mContext = context;

    }

    @Override
    public void onBindData(CommViewHolder holder, Expend expend, int position) {

        TextView tv_expend_option = holder.getView(R.id.tv_expend_option);
        //tv_expend_option.setText(s);


        NineGridView nineGrid = holder.getView(R.id.nineGrid);


        List<String> imageUrl = expend.getImageUrl();
        if (imageUrl != null) {
            ArrayList<ImageInfo> imageInfo = new ArrayList<>();
            for (int i = 0; i < imageUrl.size(); i++) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(imageUrl.get(i));
                info.setBigImageUrl(imageUrl.get(i));
                imageInfo.add(info);
            }
            nineGrid.setAdapter(new NineGridViewClickAdapter(mContext, imageInfo));
        }


    }


}
