package djjtest.com.androiddemo.utils;

import android.content.Context;
import android.content.res.Resources;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import djjtest.com.androiddemo.Constants;

/**
 * Author      :    DongJunJie
 * Date        :    2018/12/18
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class CommonUtils {

    static WeakReference<Toast> toastWeakReference;

    /**
     * Toast 提示框
     *
     * @param context
     * @param content
     */
    public static void showToast(Context context, String content) {

        Toast toast;
        if (toastWeakReference == null || toastWeakReference.get() == null) {
            toast = Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_SHORT);
            toastWeakReference = new WeakReference<>(toast);
        }
        toast = toastWeakReference.get();
        toast.setText(content);
        toast.show();
    }

    public static String TAG = "DJJTEST";

    public static void log(Object... objects) {
        log2(true, TAG, objects);
    }

    public static void log(Map map) {
        Set<Map.Entry> set = map.entrySet();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : set) {
            sb.append(entry.getKey()).append(" :").append(entry.getValue()).append(";");
        }
        log2(true, TAG, sb.toString());
    }

    public static void dLog(Object... objects) {
        log2(false, TAG, objects);
    }

    public static void dLog(Map map) {
        Set<Map.Entry> set = map.entrySet();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : set) {
            sb.append(entry.getKey()).append(" :").append(entry.getValue()).append(";");
        }
        log2(false, TAG, sb.toString());
    }

    /**
     * 封装打印LOG，方便观察更多细节
     *
     * @param releaseShow true 在release包也打印
     * @param TAG         tag
     * @param objects     需要打印的对象
     */
    public static void log2(boolean releaseShow, String TAG, Object... objects) {
        if (!releaseShow && !Constants.IS_TEST) {
            return;
        }
        if (objects == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        Throwable a = new Throwable();
        StackTraceElement[] traceElement = a.getStackTrace();
        sb.append(" \n╔═════════════════════════════════");
        sb.append("\n║➨➨at ");
        sb.append(traceElement[2]);
        sb.append("\n║➨➨➨➨at ");
        sb.append(traceElement[3]);
        sb.append("\n╟───────────────────────────────────\n");
        sb.append("║");
        for (Object o : objects) {
            if (o != null) {
                sb.append(o.toString());
            } else {
                sb.append("null");
            }
            sb.append("___");
        }
        sb.append("\n╚═════════════════════════════════");
        Log.e(TAG, sb.toString());
    }

    public static boolean listIsNull(List list) {
        return !(list != null && list.size() > 0);
    }




    public static int dp2px(Resources resources, float dpValue) {
        final float scale = resources.getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static InputFilter[] getIDcardInputFilter(final EditText editText) {
        String[] IDCARD = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "x", "X",};
        final List<String> idCardList = Arrays.asList(IDCARD);
        InputFilter inputFilter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                // 返回空字符串，就代表匹配不成功，返回null代表匹配成功
                for (int i = 0; i < source.toString().length(); i++) {
                    if (!idCardList.contains(String.valueOf(source.charAt(i)))) {
                        return "";
                    }
                    if (editText.getText().toString().length() < 17) {
                        if ("x".equals(String.valueOf(source.charAt(i))) || "X".equals(String.valueOf(source.charAt(i)))) {
                            return "";
                        }
                    }
                }
                return null;
            }
        };
        return new InputFilter[]{new InputFilter.LengthFilter(18), inputFilter};
    }
}
