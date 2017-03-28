package com.necer.carexpend.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.necer.carexpend.R;
import com.necer.carexpend.bean.Expend;
import com.necer.carexpend.utils.ExpendItemUtils;

import java.util.ArrayList;
import java.util.List;

import necer.nrecyclerview.NAdapter;
import necer.nrecyclerview.NRecyclerViewHolder;

/**
 * Created by necer on 2017/2/4.
 */

public class HomeAdapter extends NAdapter<Expend> {
    public HomeAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_home;
    }

    @Override
    protected void onBindData(NRecyclerViewHolder holder, final Expend expend, int position) {
        TextView tv_expend_option = holder.getView( R.id.tv_expend_option);
        TextView tv_createTime = holder.getView( R.id.tv_createTime);
        TextView tv_delete = holder.getView( R.id.tv_delete);
        tv_delete.setVisibility(TextUtils.isEmpty(expend.getObjectId()) ? View.GONE : View.VISIBLE);
        TextView tv_content = holder.getView(R.id.tv_content);
        ImageView avatar = holder.getView(R.id.avatar);
        NineGridView nineGrid = holder.getView( R.id.nineGrid);

        tv_expend_option.setText(ExpendItemUtils.getNameByType(expend.getType()) + " — " + expend.getMoney() + "        " + expend.getDate());
        tv_createTime.setText(expend.getCreateTime());
        avatar.setImageResource(ExpendItemUtils.getIconByType(expend.getType()));
        tv_content.setText(expend.getDescribe());

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
