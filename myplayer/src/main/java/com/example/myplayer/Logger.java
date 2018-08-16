package com.example.myplayer;

import android.text.TextUtils;
import android.util.Log;

public final class Logger {

    private static final boolean DEBUG = true;

    private static final String TAG = "myplayer";

    public static void i(String tag, String... logs) {
        Log.i(getTag(tag), getLogs(logs));
    }

    public static void d(String tag, String... logs) {
        if (!DEBUG) {
            return;
        }
        Log.d(getTag(tag), getLogs(logs));
    }

    private static String getTag(String tag) {
        return TextUtils.isEmpty(tag) ? TAG : tag;
    }

    private static String getLogs(String... logs) {
        if (logs == null || logs.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String s : logs) {
            sb.append("  ");
            sb.append(s);
        }
        return sb.toString();
    }
}
