package in.srain.cube.views.ptr.loadmore.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sinye on 16/11/4
 */

public abstract class RefreshAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final String TAG = "RefreshAdapter";
    public static final int TYPE_FOOTER = -1;
    public static final int TYPE_CONTENT = 1;
    private View footer;
    private List<HeaderModel> headers = new ArrayList<>();
    private int TYPE_HEADER = 1000001;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //header support
        if(containsViewType(viewType)){
            return new HeaderViewHolder(findViewByViewType(viewType));
        }
        //load more footer
        if (TYPE_FOOTER == viewType) {
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footer.setLayoutParams(layoutParams);
            return new FooterViewHolder(footer);
        }
        //common data
        return onCreateContentViewHolder(parent, viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //返回header
        if(headers != null && position < headers.size()){
            return;
        }
        //返回footer
        if (footer != null && position == getItemCount()-1) {
            return;
        }
        //返回common data
        onBindContentViewHolder(holder, position-headers.size());
    }

    @Override
    public int getItemCount() {
        int itemCount = getContentItemCount();
        if(headers != null && headers.size() > 0){
            itemCount += headers.size();
        }
        if (footer != null) {
            itemCount++;
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        //support headers
        if(headers != null && position < headers.size()){
            return headers.get(position).type;
        }
        //support load more footer
        if (footer != null && position == getItemCount() -1) {
            return TYPE_FOOTER;
        }
        //common data
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
        footer = view;
    }


    /****************************  header support  ***************************/
    public void addHeader(View view){
        headers.add(new HeaderModel(view));
    }

    class HeaderModel{
        private View view;
        private int type;

        public HeaderModel(View view){
            this.view = view;
            this.type = TYPE_HEADER;
            TYPE_HEADER ++ ;
        }
    }

    public boolean containsViewType(int viewType){
        boolean contains = false;
        for(HeaderModel model : headers){
            if(model.type == viewType){
                contains = true;
                break;
            }
        }
        return contains;
    }

    public View findViewByViewType(int viewType){
        View view = null;
        for(HeaderModel model : headers){
            if(model.type == viewType){
                view = model.view;
                break;
            }
        }
        return view;
    }

}
