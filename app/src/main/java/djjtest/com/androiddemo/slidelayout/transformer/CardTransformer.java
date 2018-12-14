package djjtest.com.androiddemo.slidelayout.transformer;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * Author      :    DongJunJie
 * Date        :    2018/12/4
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class CardTransformer implements ViewPager.PageTransformer {

    private int mOffset = 60;

    @Override
    public void transformPage(View page, float position) {
        Log.e("djjtest", "page transformPage" + page.getTag() + " position:" + position);


        //大于0 是不滑动的 只改变大小
        if (position >= 0) {
            page.setRotation(0);
            //移动X轴坐标，使得卡片在同一坐标
            page.setTranslationX(-position * page.getWidth());
            //缩放卡片并调整位置
            float scale = (page.getWidth() - mOffset * position) / page.getWidth();
            page.setScaleX(scale);
            page.setScaleY(scale);
            //移动Y轴坐标
            page.setTranslationY(position * mOffset);
            Log.e("djjtest", "page111 " + page.getTag() +
                    " position:" + position + "  " +
                    (page.getWidth() / 2 * position) + "  getLeft" +
                    page.getLeft() + "  getTranslationX:" + page.getTranslationX() +
                    "   x:" + page.getX());
            return;
        }

        float translationX = position;
        float rotation = position * 90;

        if (!callBack.isSlideLeft() && !callBack.isBacK()) {
            translationX = -1 * position * 2 + 1;
            rotation = -rotation;
        }
        page.getPivotX();
        Log.e("djjtest", "getPivotX " + page.getPivotX() +
                " getPivotY:" + page.getPivotY() +
                "  w:" + page.getWidth() +
                "  h:" + page.getHeight());
        page.setRotation(rotation);
        float sx = ((View) page.getParent()).getScrollX();
        Log.e("djjtest", "page " + page.getTag() +
                " translationX:" + translationX +
                "  rotation" + rotation +
                " x:" + page.getX() +
                " sx:" + sx +
                " sx-x:" + (page.getX() - sx));

        page.setTranslationX(page.getWidth() * translationX);
//        page.setX(page.getWidth() * position);

    }

    public CardTransformer setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }


    CallBack callBack;

    public interface CallBack {
        boolean isSlideLeft();

        boolean isBacK();
    }
}
