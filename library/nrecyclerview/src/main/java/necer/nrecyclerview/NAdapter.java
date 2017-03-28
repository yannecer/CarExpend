package necer.nrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by necer on 2017/3/14.
 */

public abstract class NAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    protected List<T> dataList;
    protected Context mContext;
    protected LayoutInflater layoutInflater;
    protected List<View> mHeadList;
    private List<Integer> mHeadViewTypeLists;

    private View mLoadMoreView;
    private String[] swipeMenu;//侧滑菜单
    private static int ITEM_NOMAL = 100;
    private static int ITEM_FOOT = 200;

    public NAdapter(Context context) {
        mContext = context;
        dataList = new ArrayList<>();
        mHeadList = new ArrayList<>();
        mHeadViewTypeLists = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
    }

    public NAdapter(Context context, String[] swipeMenu) {
        this(context);
        this.swipeMenu = swipeMenu;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mHeadViewTypeLists.contains(viewType)) {
            return new HeadAndFootViewHolder(mHeadList.get(viewType));
        } else if (viewType == ITEM_FOOT && mLoadMoreView != null) {
            return new HeadAndFootViewHolder(mLoadMoreView);
        } else {
            if (swipeMenu!=null) {
                View contentView = layoutInflater.inflate(getItemLayoutId(), parent, false);
                contentView.measure(0, 0);
                int measuredHeight = contentView.getMeasuredHeight();
                View swipMenuLayout = layoutInflater.inflate(R.layout.item_swip, null, false);
                final SwipeItemLayout swipeItemLayout = new SwipeItemLayout(mContext);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, measuredHeight);
                swipeItemLayout.setLayoutParams(layoutParams);
                swipeItemLayout.addMenus(swipMenuLayout);
                swipeItemLayout.addView(contentView, swipeItemLayout.getChildCount());
                final RecyclerView.ViewHolder viewHolder = new NRecyclerViewHolder(swipeItemLayout);
                TextView tv_fitst = (TextView) swipMenuLayout.findViewById(R.id.tv_first);
                TextView tv_second = (TextView) swipMenuLayout.findViewById(R.id.tv_second);
                tv_fitst.setText(swipeMenu[0]);
                tv_second.setText(swipeMenu[1]);

                tv_fitst.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (onSwipeClickListener != null) {
                                    onSwipeClickListener.onClickFirst(viewHolder.getAdapterPosition());
                                    swipeItemLayout.close();
                                }
                            }
                        });
                tv_second.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onSwipeClickListener != null) {
                            onSwipeClickListener.onClickSecond(viewHolder.getAdapterPosition());
                            swipeItemLayout.close();
                        }
                    }
                });
                return viewHolder;
            } else {
                View contentView = layoutInflater.inflate(getItemLayoutId(), parent, false);
                RecyclerView.ViewHolder viewHolder =new NRecyclerViewHolder(contentView);
                return viewHolder;
            }
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_NOMAL) {
            onBindData((NRecyclerViewHolder)holder,dataList.get(position-mHeadList.size()),position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickItemListener != null) {
                        onClickItemListener.onClickItem(holder, holder.getAdapterPosition());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() + mHeadList.size() + (mLoadMoreView == null ? 0 : 1);
    }

    @Override
    public int getItemViewType(int position) {
        mHeadViewTypeLists.clear();
        if (position < mHeadList.size()) {
            mHeadViewTypeLists.add(position);
            return position;
        } else if (position >= mHeadList.size() + dataList.size()) {
            return ITEM_FOOT;
        } else {
            return ITEM_NOMAL;
        }
    }

    public void addItem(T t) {
        dataList.add(0, t);
        notifyDataSetChanged();

    }

    public void delItem(T t) {
        if (dataList.contains(t)) {
            dataList.remove(t);
            notifyDataSetChanged();
        }
    }

    public void addData(List<T> list) {
     //   removeLoadMoreView();
        MyLog.d("removeLoadMoreView");
        dataList.addAll(dataList.size(),list);
        notifyDataSetChanged();
    }
    public void replaceData(List<T> list) {
      //  removeLoadMoreView();
        dataList.clear();
        dataList.addAll(0, list);
        notifyDataSetChanged();
    }
    public void clear() {
        removeLoadMoreView();
        dataList.clear();
        notifyDataSetChanged();
    }

    protected abstract int getItemLayoutId();

    protected abstract void onBindData(NRecyclerViewHolder holder,T t,int position);

    public void addHeaderView(View headView) {
        mHeadList.add(headView);
        notifyDataSetChanged();
    }
    protected void addLoadMoreView(View footerView) {
        if (mLoadMoreView != null) {
            throw new RuntimeException("RecyclerView已经有footerView!");
        }
        this.mLoadMoreView = footerView;
        notifyDataSetChanged();
    }

    public boolean hasLoadMoreView() {
        return mLoadMoreView == null ? false : true;
    }
    public void removeLoadMoreView() {
        mLoadMoreView = null;
    }

    static class HeadAndFootViewHolder extends RecyclerView.ViewHolder {
        public HeadAndFootViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnClickItemListener {

        void onClickItem(RecyclerView.ViewHolder holder, int position);
    }

    private OnClickItemListener onClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public interface OnSwipeClickListener {
        void onClickFirst(int position);
        void onClickSecond(int position);
    }

    private OnSwipeClickListener onSwipeClickListener;

    public void setOnSwipeClickListener(OnSwipeClickListener onSwipeClickListener) {
        this.onSwipeClickListener = onSwipeClickListener;
    }
}

