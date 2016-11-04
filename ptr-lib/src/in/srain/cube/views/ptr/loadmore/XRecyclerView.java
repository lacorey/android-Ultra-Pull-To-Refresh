package in.srain.cube.views.ptr.loadmore;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.R;
import in.srain.cube.views.ptr.loadmore.adapter.RefreshAdapter;

/**
 * Created by sinye on 16/11/4
 */

public class XRecyclerView extends FrameLayout{
    private View mEmptyView;
    private RecyclerView mRecyclerView;
    private PtrFrameLayout mPtrFrameLayout;
    private PtrClassicDefaultHeader header;
    private DefaultFooter footer;

    private RefreshListener refreshListener;
    private OnScrollListener onScrollListener;
    private boolean enablePullRefresh; //是否支持下拉刷新
    private boolean enableLoadMore;   //是否支持加载更多
    private boolean loading;         //是否正在加载中
    private RecyclerHelper helper;
    private RefreshAdapter adapter;



    public XRecyclerView(Context context) {
        super(context);
        initViews();
    }

    public XRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public void initViews(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_recyclerview_layout, this);
        helper = RecyclerHelper.create(mRecyclerView);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (onScrollListener != null){
                    onScrollListener.onScrollStateChanged(recyclerView, newState);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (helper.findLastVisibleItemPosition() == adapter.getItemCount() - 1) {
                    // invoke autoload more.
                    if (enableLoadMore && !loading) {
                        loadMore();
                    }
//                    resetFooterHeight();
                }
                if (onScrollListener != null) {
                    onScrollListener.onScrolled(recyclerView, dx, dy);
                }
            }
        });
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.ptr_frame);
        mEmptyView = (LinearLayout) findViewById(R.id.empty_view);
        mPtrFrameLayout.setLoadingMinTime(1000);
        mPtrFrameLayout.setDurationToCloseHeader(400);
        // header
        header = new PtrClassicDefaultHeader(getContext());
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);
        mPtrFrameLayout.disableWhenHorizontalMove(true);
        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View view, View header) {
                return enablePullRefresh && !mRecyclerView.canScrollVertically(-1);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                if (refreshListener != null) {
                    refreshListener.onRefresh();
                }
            }
        });
    }

    public void loadMore(){
        if(refreshListener != null){
            refreshListener.onLoadMore();
        }
    }

    public abstract static class OnScrollListener {
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        }
    }

    private float mLastY = -1;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (adapter != null && helper.findLastVisibleItemPosition() == adapter.getItemCount() - 1
                        && deltaY < 0 && enableLoadMore && !loading) {
                    // last item, already pulled up or want to pull up.
                    startLoadMore();
                }
                break;
            default:
                mLastY = -1;
                break;

        }
        return super.dispatchTouchEvent(ev);
    }

    private void startLoadMore() {
        loading = true;
        footer.setVisibility(View.VISIBLE);
        if (refreshListener != null) {
            refreshListener.onLoadMore();
        }
    }

    public void setLoadMoreEnable(boolean enableLoadMore) {
        this.enableLoadMore = enableLoadMore;
        if (enableLoadMore && footer == null) {
            throw new IllegalArgumentException("setPullLoadEnable() must be called after setAdapter");
        }
        if (!enableLoadMore) {
            adapter.addFooter(null);
        } else {
            adapter.addFooter(footer);
        }
        footer.setVisibility(enableLoadMore ? VISIBLE : GONE);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(RefreshAdapter adapter) {
        this.adapter = adapter;
        mRecyclerView.setAdapter(adapter);
        footer = new DefaultFooter(getContext());
        adapter.addFooter(footer);
    }
}
