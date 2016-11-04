package in.srain.cube.views.ptr.loadmore.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sinye on 16/11/4
 */

public abstract class RefreshAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int TYPE_FOOTER = -1;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_CONTENT = 1;
    private View footerView;
    private List<View> headerViews;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_FOOTER == viewType) {
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(layoutParams);
            return new FooterViewHolder(footerView);
        }
        // TODO: 16/11/4 add header support
        return onCreateContentViewHolder(parent, viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (footerView != null && position >= getContentItemCount()) {
            return;
        }
        onBindContentViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return getContentItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (footerView != null && position >= getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        if(headerViews != null && headerViews.size() > 0){
            if(position < headerViews.size()){
                return TYPE_HEADER;
            }
        }
        return getContentItemViewType(position);
    }

    public abstract RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindContentViewHolder(RecyclerView.ViewHolder holder, int position);

    public abstract int getContentItemViewType(int position);

    public abstract int getContentItemCount();

    class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
    class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void addFooter(View view) {
        footerView = view;
    }

    public void addHeader(View view){
        if(headerViews == null){
            headerViews = new ArrayList<>();
        }
        headerViews.add(view);
    }

}
