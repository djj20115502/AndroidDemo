package djjtest.com.androiddemo.touch;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import djjtest.com.androiddemo.FragmentAdapter;

/**
 * Author      :    DongJunJie
 * Date        :    2018/12/4
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class CardTouchListener implements View.OnTouchListener {
    private float aPosX;
    private float aPosY;
    private float aDownTouchX;
    private float aDownTouchY;
    private float BASE_ROTATION_DEGREES = 15f;
    private final int TOUCH_ABOVE = 0;
    private final int TOUCH_BELOW = 1;

    private int touchPosition;


    FragmentAdapter pagerAdapter;

    public CardTouchListener(FragmentAdapter viewPager) {
        this.pagerAdapter = viewPager;
    }
    boolean is=true;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        Log.e("djjtest", "onTouch : " +  pagerAdapter.getCurrentPrimaryItem().getView().getTag());
        Log.e("djjtest", "onTouch : " +  event);
        if(is){
            return true;
        }

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:

                float x = 0;
                float y = 0;
                view.getParent().requestDisallowInterceptTouchEvent(true);
                try {
                    x = event.getX(0);
                    y = event.getY(0);
                } catch (IllegalArgumentException e) {
                    Log.w("djjtest", "Exception in onTouch(view, event) : " + 0, e);
                    break;
                }
                // Remember where we started
                aDownTouchX = x;
                aDownTouchY = y;

                aPosX = pagerAdapter.getCurrentPrimaryItem().getView().getX();
                aPosY = pagerAdapter.getCurrentPrimaryItem().getView().getY();

                if (y < pagerAdapter.getCurrentPrimaryItem().getView().getHeight() / 2) {
                    touchPosition = TOUCH_ABOVE;
                } else {
                    touchPosition = TOUCH_BELOW;
                }
                Log.e("djjtest", "aDownTouchX:" + aDownTouchX + " aDownTouchY" + aDownTouchY
                        + " aPosX" + aPosX + "  aPosY" + aPosY);
                break;

            case MotionEvent.ACTION_UP:

//                        resetCardViewOnStack();

                view.getParent().requestDisallowInterceptTouchEvent(false);
                break;


            case MotionEvent.ACTION_MOVE:

                final float xMove = event.getX(0);
                final float yMove = event.getY(0);

                final float dx = xMove - aDownTouchX;
                final float dy = yMove - aDownTouchY;
                Log.e("djjtest", "xMove:" + xMove + " yMove" + yMove
                        + " dx" + dx + "  dy" + dy);
                // Move the frame
                aPosX += dx;
                aPosY += dy;
//                Log.e("x,y", aPosX + "," + aPosY);
                Log.e("djjtest", "aPosX:" + aPosX + " aPosY" + aPosY
                );
                // calculate the rotation degrees
                float distobjectX = aPosX - pagerAdapter.getCurrentPrimaryItem().getView().getX();
                float rotation = BASE_ROTATION_DEGREES * 2.f * distobjectX / view.getWidth();
                if (touchPosition == TOUCH_BELOW) {
                    rotation = -rotation;
                }

                //in this area would be code for doing something with the view as the frame moves.
                pagerAdapter.getCurrentPrimaryItem().getView().setX(aPosX);
                pagerAdapter.getCurrentPrimaryItem().getView().setY(aPosY);
                pagerAdapter.getCurrentPrimaryItem().getView().setRotation(rotation);
//                        mFlingListener.onMoveXY(aPosX, aPosY);
//                        mFlingListener.onScroll(getScrollProgressPercent());
                break;

            case MotionEvent.ACTION_CANCEL: {

//                        resetCardViewOnStack();
                view.getParent().requestDisallowInterceptTouchEvent(false);
                break;
            }
        }

        return true;
    }


    public interface CallBack {
        void slipRight();

        void slipLeft();
    }
}
