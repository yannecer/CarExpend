package com.necer.carexpend.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;

import com.necer.carexpend.R;
import com.necer.carexpend.utils.RefreshLoadMoreUtil;
import com.pnikosis.materialishprogress.ProgressWheel;


public class NListView extends ListView implements OnScrollListener {
    private LayoutInflater mInflater;
    private View mFooterView;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean mIsLoadingMore = false;
    private boolean stateChanged = false;//用来判断当数据没有铺满全屏的时候，不出现footer

    private boolean hasFooterView = false;

    private ProgressWheel progressbar;
    private TextView textView;
    private int scrollState;

    public NListView(Context context) {
        super(context);
        init(context);
    }

    public NListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFooterView = mInflater.inflate(R.layout.load_more_footer, this, false);
        progressbar = (ProgressWheel) mFooterView.findViewById(R.id.progressbar);
        textView = (TextView) mFooterView.findViewById(R.id.textView);

        super.setOnScrollListener(this);
    }

    public void stopLoadMore() {
        mIsLoadingMore = false;
        textView.setText("已无更多");
        progressbar.setVisibility(GONE);
    }

    /**
     * 去掉footer，场景：一种条件下的数据很多，切换另一种条件很少，数据不能铺满屏幕。需要去掉
     */
    public void removeFooter() {
        if (hasFooterView) {
            removeFooterView(mFooterView);
            hasFooterView = false;
            stateChanged = false;
        }
    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        stateChanged = true;
        scrollState = i;

        switch (i) {
            case OnScrollListener.SCROLL_STATE_IDLE:
                int position = absListView.getFirstVisiblePosition();
                if (position == 0) {
                    //顶部
                    if (onDirectionListener != null) {
                        onDirectionListener.scrollTop();
                    }
                }
                break;
        }
    }



    private int mLastScrollY; //第一个可视的item的顶部坐标
    private int mPreviousFirstVisibleItem; //上一次滑动的第一个可视item的索引值
    private AbsListView mListView;//列表控件，如ListView
    private int mScrollThreshold;


    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        boolean contentToBottom = RefreshLoadMoreUtil.isContentToBottom(this);//状态为滑动到底部或者数据为铺满屏幕
        //item点击会调用onScroll（）方法
        if (scrollState != OnScrollListener.SCROLL_STATE_IDLE && contentToBottom && stateChanged) {
            if (!hasFooterView) {
                addFooterView(mFooterView,null,false);
                mFooterView.setVisibility(View.VISIBLE);
                hasFooterView = true;
            }
            if (!mIsLoadingMore) {
                textView.setText("正在加载...");
                progressbar.setVisibility(VISIBLE);
                mIsLoadingMore = true;
                if (mOnLoadMoreListener != null) {
                    mOnLoadMoreListener.onLoadMore();
                }
            }
        }

        /***滑动方向***/

        if (totalItemCount != 0) {
            // 滑动距离：不超过一个item的高度
            if (this.isSameRow(firstVisibleItem)) {
                int newScrollY = this.getTopItemScrollY();
                //判断滑动距离是否大于 mScrollThreshold
                boolean isSignificantDelta = Math.abs(this.mLastScrollY - newScrollY) > this.mScrollThreshold;
                if (isSignificantDelta) {
                    //对于第一个可视的item，根据其前后两次的顶部坐标判断滑动方向
                    if (this.mLastScrollY > newScrollY) {
                        if (onDirectionListener != null) {
                            onDirectionListener.scrollUp();
                        }
                    } else {
                        if (onDirectionListener != null) {
                            onDirectionListener.scrollDown();
                        }
                    }
                }
                this.mLastScrollY = newScrollY;
            } else {//根据第一个可视Item的索引值不同，判断滑动方向
                if (firstVisibleItem > this.mPreviousFirstVisibleItem) {
                    if (onDirectionListener != null) {
                        onDirectionListener.scrollUp();
                    }
                } else {
                    if (onDirectionListener != null) {
                        onDirectionListener.scrollDown();
                    }
                }
                this.mLastScrollY = this.getTopItemScrollY();
                this.mPreviousFirstVisibleItem = firstVisibleItem;
            }
        }

        /**滑动方向***/


    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }


    /**
     * 以下辅助SwipeMenuListView
     */
    private boolean isSliding = true;//是否可侧滑
    private SwipeRefreshLayout swipeRefreshLayout;

    /**
     * SwipeMenuListView和SwipeRefreshLayout一起使用时，侧滑时会和SwipeRefreshLayout冲突，
     * 传过来SwipeRefreshLayout在侧滑时禁止下滑
     *
     * @param swipeRefreshLayout
     */
    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;

    }

    public void setIsSliding(boolean isSliding) {
        this.isSliding = isSliding;
    }


    private float mDownX;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isSliding) {
            switch (ev.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    mDownX = ev.getX();

                    break;
                case MotionEvent.ACTION_MOVE:
                    float dx = Math.abs((ev.getX() - mDownX));
                    //如果发生左右的位移，则把swipeRefreshLayout禁掉
                    if (dx > 0) {
                        if (null != swipeRefreshLayout) {
                            swipeRefreshLayout.setEnabled(false);
                        }

                    }
                    break;
                case MotionEvent.ACTION_UP:
                    //手指抬起时，让swipeRefreshLayout可用
                    if (null != swipeRefreshLayout) {
                        swipeRefreshLayout.setEnabled(true);
                    }
                    break;
            }
        }

        return super.onTouchEvent(ev);
    }


    public void setScrollThreshold(int scrollThreshold) {
        this.mScrollThreshold = scrollThreshold;
    }

    public void setListView(@NonNull AbsListView listView) {
        this.mListView = listView;
    }

    private boolean isSameRow(int firstVisibleItem) {
        return firstVisibleItem == this.mPreviousFirstVisibleItem;
    }

    private int getTopItemScrollY() {
        if (this.mListView != null && this.mListView.getChildAt(0) != null) {
            View topChild = this.mListView.getChildAt(0);
            return topChild.getTop();
        } else {
            return 0;
        }
    }

    public interface OnDirectionListener {
        void scrollUp();

        void scrollDown();

        void scrollTop();//顶部
    }

    private OnDirectionListener onDirectionListener;

    public void setOnDirectionListener(OnDirectionListener onDirectionListener) {
        this.onDirectionListener = onDirectionListener;
    }

}
