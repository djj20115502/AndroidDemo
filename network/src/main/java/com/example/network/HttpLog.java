package com.example.network;

import android.util.Log;

/**
 * Author      :    DongJunJie
 * Date        :    2019/3/1
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class HttpLog {

    public static void log(Object... objects) {
        logS(true, objects);
    }

    public static void logS(boolean showLog, Object... objects) {
        if (!showLog) {
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
        Log.e("HttpLog", sb.toString());
    }
}
