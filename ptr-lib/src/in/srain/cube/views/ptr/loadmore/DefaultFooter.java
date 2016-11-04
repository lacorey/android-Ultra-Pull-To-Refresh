package in.srain.cube.views.ptr.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import in.srain.cube.views.ptr.R;

/**
 * Created by sinye on 16/11/4
 */

public class DefaultFooter extends FrameLayout {
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
    }
}
