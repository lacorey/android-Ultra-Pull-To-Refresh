package in.srain.cube.views.ptr.demo.ui.classic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.demo.R;
import in.srain.cube.views.ptr.loadmore.adapter.RefreshAdapter;

/**
 * Created by sinye on 16/11/17
 */

public class TestAdapter extends RefreshAdapter{
    private List<String> mList;
    private Context mContext;
    public static final int TYPE_CONTENT = 2;

    public TestAdapter(Context context){
        this.mContext = context;
        mList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_recycler, parent, false);
        return new ContentHolder(layout);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, int position) {
        String str = mList.get(position);
        ((ContentHolder) holder).content.setText(str);
    }

    @Override
    public int getContentItemViewType(int position) {
        return TYPE_CONTENT;
    }

    @Override
    public int getContentItemCount() {
        return mList.size();
    }

    public void refreshData(List<String> list ){
        mList.addAll(list);
        this.notifyDataSetChanged();
    }

    class ContentHolder extends RecyclerView.ViewHolder {
        private TextView content;

        public ContentHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }

    public void addData(String str){
        mList.add(str);
        this.notifyDataSetChanged();
    }

    public int getSpanSize(int position){
        if(position == getItemCount() - 1){
            return 2;
        }else{
            return 1;
        }
    }
}
