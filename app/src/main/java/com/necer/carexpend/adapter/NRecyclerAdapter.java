package com.necer.carexpend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.necer.carexpend.R;
import com.necer.carexpend.application.Constant;
import com.necer.carexpend.bean.Expend;
import com.necer.carexpend.view.NRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by necer on 2017/1/4.
 */
public class NRecyclerAdapter extends RecyclerView.Adapter<NRecyclerViewHolder> {

    private Context mContext;
    private List<Expend> mList;
    private View mFooterView;


    private final int TYPE_NORMAL = 0;
    private final int TYPE_FOOTER = 2;

    public NRecyclerAdapter(Context context, List<Expend> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public NRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER && mFooterView != null) {
            NRecyclerViewHolder viewHolder = new NRecyclerViewHolder(mFooterView, parent);
            return viewHolder;
        }
        if (viewType == TYPE_NORMAL) {
            View view = View.inflate(mContext, R.layout.item_home, null);
            NRecyclerViewHolder viewHolder = new NRecyclerViewHolder(view, parent);

           /* gridView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClickItem(viewHolder.getAdapterPosition());
                    }
                }
            });*/


            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(NRecyclerViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == TYPE_FOOTER) {
            return;
        }
        if (itemViewType == TYPE_NORMAL) {
            TextView tv_expend_option = holder.getView(R.id.tv_expend_option);
            TextView tv_createTime = holder.getView(R.id.tv_createTime);
            TextView tv_delete = holder.getView(R.id.tv_delete);
            ImageView avatar = holder.getView(R.id.avatar);
            NineGridView nineGrid = holder.getView(R.id.nineGrid);

            Expend expend = mList.get(position);

            tv_expend_option.setText(Constant.EXPENDITEMS[expend.getType()] + "-" + expend.getMoney());
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





        }

    }

    @Override
    public int getItemCount() {
        return mList.size() == 0 ? 0 : (mFooterView == null ? mList.size() : mList.size() + 1);
    }


    @Override
    public int getItemViewType(int position) {
        if (mFooterView != null && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }



    public void addFooterView(View mFooterView) {
        this.mFooterView = mFooterView;
        notifyItemChanged(getItemCount() - 1);
    }


    public interface OnItemClickListener {
        void onClickItem(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



}
