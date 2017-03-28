package necer.nrecyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * Created by necer on 2017/3/14.
 */
public class NRecyclerView extends RecyclerView {
    private boolean isLoadMoreEnable = false;
    private boolean isLoading = false;
    private NAdapter mAdapter;
    private View loadMoreView;

    private LinearLayout ll_loading;
    private LinearLayout ll_noMore;
    private TextView tv_message;
    private LinearLayoutManager linearLayoutManager;

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

        //当listview使用
        linearLayoutManager = new LinearLayoutManager(getContext());
        setLayoutManager(linearLayoutManager);
        //添加分隔线
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        addItemDecoration(dividerItemDecoration);
        //需要加上一个父view ，不然显示不全
        loadMoreView = LayoutInflater.from(getContext()).inflate(R.layout.layout_load_more, this, false);
        ll_loading = (LinearLayout) loadMoreView.findViewById(R.id.ll_loading);
        ll_noMore = (LinearLayout) loadMoreView.findViewById(R.id.ll_noMore);
        tv_message = (TextView) loadMoreView.findViewById(R.id.tv_message);

        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //动态添加脚
                boolean contentToBottom = RefreshLoadMoreUtil.isContentToBottom(NRecyclerView.this);//状态为滑动到底部或者数据为铺满屏幕
                if (isLoadMoreEnable && !isLoading && !mAdapter.hasLoadMoreView() && dy > 0) {
                    mAdapter.addLoadMoreView(loadMoreView);
                    MyLog.d("动态添加脚");
                }
                //自动加载更多
                if (isLoadMoreEnable && !isLoading && mAdapter.hasLoadMoreView() && contentToBottom) {
                    if (onLoadMoreListener != null) {
                        startLoadMore();
                        onLoadMoreListener.onLoadMore();
                    }
                }
                //滑动到顶部
                boolean contentToTop = RefreshLoadMoreUtil.isContentToTop(NRecyclerView.this);
                if (contentToTop) {
                    if (onDirectionListener != null) {
                        onDirectionListener.scrollTop();
                    }
                }

                //向上滑，向下滑
                if (onDirectionListener != null) {
                    if (dy > 0) {
                        onDirectionListener.scrollUp();
                    } else {
                        onDirectionListener.scrollDown();
                    }
                }
            }
        });
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if(adapter instanceof NAdapter)
        this.mAdapter = (NAdapter) adapter;
        super.setAdapter(adapter);
    }

    public void setLoadingMoreEnable(boolean isLoadMoreEnable) {
        this.isLoadMoreEnable = isLoadMoreEnable;
    }

    public void setSelection(int position) {
        linearLayoutManager.scrollToPosition(position);
    }

    public void addHeaderView(View headerView) {
        mAdapter.addHeaderView(headerView);
    }

    public void stopLoadMore() {
        isLoading = false;
        ll_loading.setVisibility(GONE);
        ll_noMore.setVisibility(VISIBLE);
    }

    private void startLoadMore() {
        isLoading = true;
        ll_noMore.setVisibility(GONE);
        ll_loading.setVisibility(VISIBLE);
    }

    public void setNoMoreMessage(String message) {
        tv_message.setText(message);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    private OnLoadMoreListener onLoadMoreListener;

    public void setLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getActionMasked();
        // 手指按下的时候，如果有开启的菜单，只要手指不是落在该Item上，则关闭菜单, 并且不分发事件。
        if (action == MotionEvent.ACTION_DOWN) {
            int x = (int) ev.getX();
            int y = (int) ev.getY();
            View openItem = findOpenItem();
            if (openItem != null && openItem != getTouchItem(x, y)) {
                SwipeItemLayout swipeItemLayout = findSwipeItemLayout(openItem);
                if (swipeItemLayout != null) {
                    swipeItemLayout.close();
                    return false;
                }
            }
        } else if (action == MotionEvent.ACTION_POINTER_DOWN) {
            // FIXME: 2017/3/22 不知道怎么解决多点触控导致可以同时打开多个菜单的bug，先暂时禁止多点触控
            return false;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 获取按下位置的Item
     */
    @Nullable
    private View getTouchItem(int x, int y) {
        Rect frame = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == VISIBLE) {
                child.getHitRect(frame);
                if (frame.contains(x, y)) {
                    return child;
                }
            }
        }
        return null;
    }

    /**
     * 找到当前屏幕中开启的的Item
     */
    @Nullable
    private View findOpenItem() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            SwipeItemLayout swipeItemLayout = findSwipeItemLayout(getChildAt(i));
            if (swipeItemLayout != null && swipeItemLayout.isOpen()) {
                return getChildAt(i);
            }
        }
        return null;
    }

    /**
     * 获取该View
     */
    @Nullable
    private SwipeItemLayout findSwipeItemLayout(View view) {
        if (view instanceof SwipeItemLayout) {
            return (SwipeItemLayout) view;
        } else if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                SwipeItemLayout swipeLayout = findSwipeItemLayout(group.getChildAt(i));
                if (swipeLayout != null) {
                    return swipeLayout;
                }
            }
        }
        return null;
    }



}
