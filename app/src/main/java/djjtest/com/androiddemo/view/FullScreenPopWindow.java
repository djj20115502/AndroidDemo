package djjtest.com.androiddemo.view;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.lang.reflect.Field;

import djjtest.com.androiddemo.utils.CommonUtils;

/**
 * Author      :    DongJunJie
 * Date        :    2019/1/23
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public abstract class FullScreenPopWindow {
    public PopupWindow popupWindow;
    private int toolbarHeight = 0;

     protected void initPopWindow(Activity activity, View contentView) {
        popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                true);
//         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//              popupWindow.setWindowLayoutType(WindowManager.LayoutParams.TYPE_TOAST);
//         } else {
             try {
                 Field field =  popupWindow.getClass().getDeclaredField("mWindowLayoutType");
                 field.setAccessible(true);
                 field.set( popupWindow, WindowManager.LayoutParams.TYPE_TOAST );
             } catch (NoSuchFieldException e) {
                 e.printStackTrace();
             } catch (IllegalAccessException e) {
                 e.printStackTrace();
             }
//         }
        popupWindow.setClippingEnabled(false);
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        CommonUtils.log("toolbarHeight",toolbarHeight);
        toolbarHeight = frame.top;
    }

    protected abstract View getContentView();

    protected abstract View getTokenView();

    protected abstract Activity getActivity();

    @CallSuper
    public void show() {
        if (popupWindow == null) {
            initPopWindow(getActivity(), getContentView());
        }
        popupWindow.showAtLocation(getTokenView(), Gravity.NO_GRAVITY, 0, 0);


//        popupWindow.getContentView().setPadding(0, 0, 0, 0);
//
//        popupWindow.getContentView().setBackgroundColor(0x00ffffff);
    }

}