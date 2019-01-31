package djjtest.com.androiddemo.slidelayout;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

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
    private float aPosX0;
    private float aPosY0;
    CallBack callBack;

    public CardTouchListener(CallBack callBack) {
        this.callBack = callBack;
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        Log.e("djjtest222", view.getTag() + "onTouch : " + event);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                view.getParent().requestDisallowInterceptTouchEvent(true);
                aDownTouchX = event.getX(0);
                aDownTouchY = event.getY(0);

                // Remember where we started

                aPosX = view.getX();
                aPosY = view.getY();
                aPosX0 = aPosX;
                aPosY0 = aPosY;
                Log.e("ACTION_DOWN", view.getTag() + "aPosX : " + aPosX + " aPosY" + aPosY);
                break;

            case MotionEvent.ACTION_UP:

                actionUp(view, event);
                if (view.getParent() != null) {
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;


            case MotionEvent.ACTION_MOVE:

                final float xMove = event.getX(0);
                final float yMove = event.getY(0);

                final float dx = xMove - aDownTouchX;
                final float dy = yMove - aDownTouchY;

                aPosX += dx;
                aPosY += dy;

                Log.e("djjtest", "aDownTouchX:" + aDownTouchX + " dx" + dx + " aPosX" + aPosX + "  getX:" + event.getX(0));
                //in this area would be code for doing something with the view as the frame moves.
                view.setX(aPosX);
                view.setY(aPosY);

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

    private void actionUp(View view, MotionEvent event) {
        float xmoveLen = view.getX() - aPosX0;
        float threshold = view.getWidth() * 0.5f;
        Log.e("djjtest", "xmoveLen:" + xmoveLen + " threshold" + threshold);
        if (xmoveLen < 0 && xmoveLen < -1 * threshold) {
            if (callBack.slipLeft()) {
                return;
            }

        }
        if (xmoveLen > 0 && xmoveLen > threshold) {
            if (callBack.slipRight()) {
                return;
            }
        }
        reset(view);
    }

    private long RESET_ANIMATOR_TIME = 500l;

    private void reset(final View view) {
        Log.e("djjtest", " reset end:x " + view.getX() + " y:" + view.getY());
        Log.e("djjtest", " reset start:x " + aPosX0 + "  y" + aPosY0);
        ObjectAnimator.ofFloat(view, "X", view.getX(), aPosX0).setDuration(RESET_ANIMATOR_TIME).start();
        ObjectAnimator.ofFloat(view, "Y", view.getY(), aPosY0).setDuration(RESET_ANIMATOR_TIME).start();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("djjtest", "reset xxxxx   " + view.getX() + " yy" + view.getY());
            }
        }, RESET_ANIMATOR_TIME);
    }


    public interface CallBack {
        boolean slipRight();

        boolean slipLeft();
    }
}
