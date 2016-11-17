package in.srain.cube.views.ptr.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import in.srain.cube.views.ptr.R;

/**
 * Created by sinye on 16/11/4
 */

public class DefaultFooter extends FrameLayout {
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_NORMAL = 2;
    private LinearLayout footerContent;
    private TextView hintText;
    private ProgressBar progressBar;

    public DefaultFooter(Context context) {
        super(context);
        initFooterView();
    }

    public DefaultFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFooterView();
    }

    public DefaultFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFooterView();
    }

    public void initFooterView(){
        View footer = LayoutInflater.from(getContext()).inflate(R.layout.view_default_footer, this);
        footerContent = (LinearLayout) footer.findViewById(R.id.footer_content);
        hintText = (TextView) footer.findViewById(R.id.hint);
        progressBar = (ProgressBar) footer.findViewById(R.id.progress);
    }

    public void showStatus(int status){
        switch (status){
            case STATUS_LOADING:
                hintText.setText("加载中...");
                progressBar.setVisibility(View.VISIBLE);
                break;
            case STATUS_NORMAL:
                hintText.setText("加载更多");
                progressBar.setVisibility(View.GONE);
                break;

        }
    }

}
