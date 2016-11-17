package in.srain.cube.views.ptr.demo.ui.classic;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.mints.base.TitleBaseFragment;
import in.srain.cube.views.ptr.demo.R;
import in.srain.cube.views.ptr.loadmore.RefreshListener;
import in.srain.cube.views.ptr.loadmore.XRecyclerView;

/**
 * Created by sinye on 16/11/4
 */

public class TestListFragment extends TitleBaseFragment {
    public static final String TAG = "TestListFragment";
    private XRecyclerView recyclerView;
    private TestAdapter mAdapter;

    private Handler mHandler = new Handler();

    private List<String> list;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View contentView = inflater.inflate(R.layout.fragment_test_recycler, null);
        getViews(contentView);
        return contentView;
    }

    public void getViews(View view){
        recyclerView = (XRecyclerView) view.findViewById(R.id.ptr_recycler);
        mAdapter = new TestAdapter(getContext());
        recyclerView.setAdapter(mAdapter);

        Button btn = new Button(getContext());
        btn.setText("click me");
        Button btn1 = new Button(getContext());
        btn1.setText("dont click me");
        TextView text1 = new TextView(getContext());
        text1.setText("it's header");
//        test for add header
//        recyclerView.addHeader(btn);
//        recyclerView.addHeader(text1);
//        recyclerView.addHeader(btn1);
        recyclerView.setPullRefreshEnable(true);
        recyclerView.setLoadMoreEnable(true);
//        test list
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(),LinearLayoutManager.VERTICAL, false));

//        test grid
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(),2);
//        recyclerView.setLayoutManager(gridLayoutManager);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return mAdapter.getSpanSize(position);
//            }
//        });

        recyclerView.setRefreshListener(new RefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.stopRefresh();
                    }
                },2000);
            }

            @Override
            public void onLoadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.stopLoadMore();
                        mAdapter.addData("fejfeljflkejflkejlkfje");
                    }
                },2000);
            }
        });

        list = initData();
        mAdapter.refreshData(list);
    }

    public List<String> initData(){
        List<String> list = new ArrayList<>();
        for(int i=0;i<20;i++){
            list.add("abcedfafeifjeifjkejkfjekf"+i);
        }
        return list;
    }
}
