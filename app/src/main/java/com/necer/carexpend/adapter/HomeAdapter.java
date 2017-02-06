package com.necer.carexpend.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.necer.carexpend.R;
import com.necer.carexpend.application.Constant;
import com.necer.carexpend.bean.Expend;
import com.necer.carexpend.view.NListAdapter;
import com.necer.carexpend.view.NListViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by necer on 2017/2/4.
 */

public class HomeAdapter extends NListAdapter<Expend> {


    public HomeAdapter(Context contxt, int layoutId) {
        super(contxt, layoutId);
    }

    @Override
    public void onBindData(View view, int position, final Expend expend) {


        TextView tv_expend_option = NListViewHolder.getView(view, R.id.tv_expend_option);
        TextView tv_createTime = NListViewHolder.getView(view, R.id.tv_createTime);
        TextView tv_delete = NListViewHolder.getView(view, R.id.tv_delete);
        ImageView avatar = NListViewHolder.getView(view, R.id.avatar);
        NineGridView nineGrid = NListViewHolder.getView(view, R.id.nineGrid);


        tv_expend_option.setText(Constant.EXPENDITEMS[expend.getType()] + "--" + expend.getMoney()+"        "+expend.getDate());
        tv_createTime.setText(expend.getCreatedAt());
        avatar.setImageResource(Constant.EXPENDICON[expend.getType()]);

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

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteListener != null) {
                    onDeleteListener.onDelete(expend);
                }
            }
        });
    }


    public interface OnDeleteListener {
        void onDelete(Expend expend);

    }

    private OnDeleteListener onDeleteListener;
    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

}
