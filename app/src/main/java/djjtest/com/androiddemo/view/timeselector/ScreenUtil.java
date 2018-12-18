package djjtest.com.androiddemo.view.timeselector;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Author      :    DongJunJie
 * Date        :    2018/12/18
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class ScreenUtil {
    public static int height;
    public static int width;
    private Context context;

    private static ScreenUtil instance;

    private ScreenUtil(Context context) {
        this.context = context;
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
    }

    public static ScreenUtil getInstance(Context context) {
        if (instance == null) {
            instance = new ScreenUtil(context);
        }
        return instance;
    }


    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 得到手机屏幕的宽度, pix单位
     */
    public int getScreenWidth() {
        return width;
    }


}