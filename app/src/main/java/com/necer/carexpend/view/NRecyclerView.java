package com.necer.carexpend.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import com.necer.carexpend.R;
import com.necer.carexpend.adapter.NRecyclerAdapter;
import com.necer.carexpend.utils.RefreshLoadMoreUtil;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * Created by necer on 2017/1/4.
 */

public class NRecyclerView extends RecyclerView {


    private NRecyclerAdapter adapter;

    private int scrollState;

    private boolean mIsLoadingMore = false;
    private boolean stateChanged = false;//用来判断当数据没有铺满全屏的时候，不出现footer

    private boolean hasFooterView = false;

    private View mFooterView;
    private ProgressWheel progressbar;
    private TextView textView;

    public NRecyclerView(Context context) {
        super(context);
        init();
    }

    public NRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

        mFooterView = View.inflate(getContext(), R.layout.load_more_footer,null);
       // mFooterView.setPadding(0, 20, 0, 20);
        progressbar = (ProgressWheel) mFooterView.findViewById(R.id.progressbar);
        textView = (TextView) mFooterView.findViewById(R.id.textView);

        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                stateChanged = true;
                scrollState = newState;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean contentToBottom = RefreshLoadMoreUtil.isContentToBottom(NRecyclerView.this);//状态为滑动到底部或者数据为铺满屏幕

                //item点击会调用onScroll（）方法
                if (scrollState != AbsListView.OnScrollListener.SCROLL_STATE_IDLE && contentToBottom && stateChanged) {
                    if (!hasFooterView) {
                        adapter.addFooterView(mFooterView);
                        mFooterView.setVisibility(View.VISIBLE);
                        hasFooterView = true;
                    }
                    if (!mIsLoadingMore) {
                        textView.setText("正在加载...");
                        progressbar.setVisibility(VISIBLE);
                        mIsLoadingMore = true;
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void setAdapter(Adapter adapter) {
        this.adapter = (NRecyclerAdapter) adapter;
        super.setAdapter(adapter);

    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
    private OnLoadMoreListener onLoadMoreListener;
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void stopLoadMore() {
        mIsLoadingMore = false;
        textView.setText("已无更多");
        progressbar.setVisibility(GONE);
    }





}
