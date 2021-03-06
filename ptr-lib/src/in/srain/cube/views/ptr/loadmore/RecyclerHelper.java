package in.srain.cube.views.ptr.loadmore;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by sinye on 16/11/4
 */

public class RecyclerHelper {
    final RecyclerView recyclerView;
    final RecyclerView.LayoutManager layoutManager;

    RecyclerHelper(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.layoutManager = recyclerView.getLayoutManager();
    }

    public static RecyclerHelper create(RecyclerView recyclerView) {
        if (recyclerView == null) {
            throw new NullPointerException("Recycler View is null");
        }
        return new RecyclerHelper(recyclerView);
    }

    public int count(){
        return layoutManager == null ? 0 : layoutManager.getItemCount();
    }

    public int findLastVisibleItemPosition() {
        if(layoutManager == null) {
            return RecyclerView.NO_POSITION;
        }
        final View child = findOneVisibleChild(layoutManager.getChildCount() - 1, -1, false, true);
        return child == null ? RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
    }

    View findOneVisibleChild(int fromIndex, int toIndex, boolean completelyVisible,
                             boolean acceptPartiallyVisible) {
        OrientationHelper helper;
        if (layoutManager.canScrollVertically()) {
            helper = OrientationHelper.createVerticalHelper(layoutManager);
        } else {
            helper = OrientationHelper.createHorizontalHelper(layoutManager);
        }

        final int start = helper.getStartAfterPadding();
        final int end = helper.getEndAfterPadding();
        final int next = toIndex > fromIndex ? 1 : -1;
        View partiallyVisible = null;
        for (int i = fromIndex; i != toIndex; i += next) {
            final View child = layoutManager.getChildAt(i);
            final int childStart = helper.getDecoratedStart(child);
            final int childEnd = helper.getDecoratedEnd(child);
            if (childStart < end && childEnd > start) {
                if (completelyVisible) {
                    if (childStart >= start && childEnd <= end) {
                        return child;
                    } else if (acceptPartiallyVisible && partiallyVisible == null) {
                        partiallyVisible = child;
                    }
                } else {
                    return child;
                }
            }
        }
        return partiallyVisible;
    }
}
