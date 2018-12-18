package djjtest.com.androiddemo.utils;

import android.util.Log;

import djjtest.com.androiddemo.Constants;

/**
 * Author      :    DongJunJie
 * Date        :    2018/12/18
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class CommonUtils {

    public static void log(Object... objects) {
        if (!Constants.IS_TEST) {
            return;
        }
        if (objects == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Object o : objects) {
            if (o != null) {
                sb.append(o.toString());
                sb.append("___");
            }
        }
        Log.e("djjtest", sb.toString());
    }
}
