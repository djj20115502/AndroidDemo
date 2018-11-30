package djjtest.com.androiddemo.slidelayout.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Author      :    DongJunJie
 * Date        :    2018/11/30
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class SlideLayout extends RecyclerView.LayoutParams {

    public SlideLayout(Context c, AttributeSet attrs) {
        super(c, attrs);
    }

    public SlideLayout(int width, int height) {
        super(width, height);
    }

    public SlideLayout(ViewGroup.MarginLayoutParams source) {
        super(source);
    }

    public SlideLayout(ViewGroup.LayoutParams source) {
        super(source);
    }

    public SlideLayout(RecyclerView.LayoutParams source) {
        super(source);
    }
}
