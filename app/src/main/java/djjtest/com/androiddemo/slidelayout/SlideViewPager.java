package djjtest.com.androiddemo.slidelayout;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import djjtest.com.androiddemo.slidelayout.transformer.CardTransformer;

/**
 * Author      :    DongJunJie
 * Date        :    2018/12/4
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class SlideViewPager extends ViewPager {

    boolean isSlideLeft = true;
    boolean isBack = false;

    public SlideViewPager(@NonNull Context context) {
        this(context, null);
    }

    public SlideViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }


    @Override
    public void setAdapter(@Nullable PagerAdapter adapter) {
        super.setAdapter(adapter);
        cardTouchListener = new CardTouchListener(new CardTouchListener.CallBack() {
            @Override
            public boolean slipRight() {
                return performSlipRight(true);
            }

            @Override
            public boolean slipLeft() {
                return performSlipLeft(true);
            }
        });

        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                getChildAt(0).setOnTouchListener(cardTouchListener);
            }
        });
//        setPageTransformer(true, new CardTransformer().setCallBack(new CardTransformer.CallBack() {
//            @Override
//            public boolean isSlideLeft() {
//                return isSlideLeft;
//            }
//
//            @Override
//            public boolean isBacK() {
//                return isBack;
//            }
//        }));
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int len = getChildCount();
                for (int j = 0; j < len; j++) {
                    getChildAt(j).setOnTouchListener(cardTouchListener);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    CardTouchListener cardTouchListener;


    public boolean performSlipLeft(boolean needNotify) {
        isSlideLeft = true;
        Log.e("djjtest", "slipLeft_" + getCurrentItem());
        if (getCurrentItem() >= getAdapter().getCount() - 1) {
            return false;
        }
        setCurrentItem(getCurrentItem() + 1);
        if (callBack != null && needNotify) {
            callBack.onLeft();
        }
        return true;
    }

    public boolean performSlipRight(boolean needNotify) {
        Log.e("djjtest", "slipRight_" + getCurrentItem()+" getCount::"+getAdapter().getCount());
        isSlideLeft = false;
        if (getCurrentItem() >= getAdapter().getCount() - 1) {
            return false;
        }
        setCurrentItem(getCurrentItem() + 1);
        if (callBack != null && needNotify) {
            callBack.onRight();
        }
        return true;
    }

    CallBack callBack;

    public SlideViewPager setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public interface CallBack {
        void onLeft();

        void onRight();

        void hasNext(boolean var);

        void hasUndo(boolean var);
    }
}
