package djjtest.com.androiddemo.test.itemDecoration;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author      :    DongJunJie
 * Date        :    2018/12/19
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class GirdItemDecoration  extends RecyclerView.ItemDecoration {


    Builder builder;

    private GirdItemDecoration(Builder builder) {
        this.builder = builder;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        RecyclerView.LayoutManager lp = parent.getLayoutManager();
//        if (lp == null) {
//            return;
//        }
        int spanCount = 3;
//        if (lp instanceof GridLayoutManager) {
//            spanCount = ((GridLayoutManager) lp).getSpanCount();
//        }
        outRect.left = builder.edgeMargin;
        outRect.right = outRect.left;
        int center = builder.centerMargin / 2;
        int location = parent.getChildLayoutPosition(view) % spanCount;
        if (location < spanCount - 1) {
            outRect.right = center;
        }
        if (location > 0) {
            outRect.left = center;
        }
        outRect.bottom = builder.bottomMargin;
        outRect.top = 0;
    }

    public static final class Builder {
        private int edgeMargin = 0;
        private int centerMargin = 0;
        private int bottomMargin = 0;

        public Builder() {
        }

        public Builder edgeMargin(int val) {
            edgeMargin = val;
            return this;
        }

        public Builder centerMargin(int val) {
            centerMargin = val;
            return this;
        }

        public Builder bottomMargin(int val) {
            bottomMargin = val;
            return this;
        }

        public GirdItemDecoration build() {
            return new GirdItemDecoration(this);
        }
    }
}

