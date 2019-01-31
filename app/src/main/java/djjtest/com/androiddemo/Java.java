package djjtest.com.androiddemo;

import android.annotation.SuppressLint;

import java.util.HashMap;

import djjtest.com.androiddemo.model.TestBuilder;

/**
 * Author      :    DongJunJie
 * Date        :    2018/12/14
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class Java {

    public static void main(String[] arg) {
        TestBuilder t2 = new TestBuilder();
        HashMap<String, String> map = t2.buildParamsMap();
        System.out.println(getUnit(1));
        System.out.println(getUnit(1111111111));
        System.out.println(getUnit(111111));
    }

    @SuppressLint("DefaultLocale")
    public static String getUnit(int count) {
        if (count < 10000) {
            return "" + count;
        }
        if (count < 100000000) {
            return "" + String.format("%.1f",  count * 1.0 / 10000 ) + "万";
        }
        return "" + String.format("%.1f", count * 1.0 / 100000000) + "亿";
    }
}
