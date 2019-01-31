package djjtest.com.androiddemo.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

/**
 * Author      :    DongJunJie
 * Date        :    2019/1/21
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :   不同文字数目2端对齐工具类
 */
public class TextCenterFormat {


    /**
     * 对显示的字符串进行格式化 比如输入：出生年月 输出结果：出正生正年正月
     */
    private static String formatStr(String str, int insetSize) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int len = str.length();
        String insert = "";
        for (int i = 0; i < insetSize; i++) {
            insert = insert + "正";
        }
        StringBuilder sb = new StringBuilder(str);
        for (int i = len - 1; i > 0; i--) {
            sb.insert(i, insert);
        }
        return sb.toString();
    }

    /**
     * 对文本进行左右对齐
     *
     * @param str
     * @param maxSize 显示对齐数量
     * @return
     */
    public static SpannableString formatText(String str, int maxSize) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int len = str.length();
        SpannableString spannableString = new SpannableString(str);
        if (len == 1) {
            return spannableString;
        }
        if (len >= maxSize) {
            spannableString.setSpan(new RelativeSizeSpan((float) (maxSize * 1.0) / len), 0, len, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return spannableString;
        }
        str = formatStr(str, maxSize - 2);
        double multiple = (maxSize - len) * 1.0 / ((len - 1) * (maxSize - 2));
        spannableString = new SpannableString(str);
        for (int i = 1; i < str.length(); i = i + maxSize - 1) {
            spannableString.setSpan(new RelativeSizeSpan((float) multiple), i, i + maxSize - 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), i, i + maxSize - 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

}
