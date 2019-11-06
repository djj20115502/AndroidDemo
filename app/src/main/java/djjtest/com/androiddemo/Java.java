package djjtest.com.androiddemo;

import android.annotation.SuppressLint;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Author      :    DongJunJie
 * Date        :    2018/12/14
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class Java {

    public static void main(String[] arg) {
//        TestBuilder t2 = new TestBuilder();
//        HashMap<String, String> map = t2.buildParamsMap();
//        System.out.println(getUnit(1));
//        System.out.println(getUnit(1111111111));
//        System.out.println(getUnit(111111));

    }

    @SuppressLint("DefaultLocale")
    public static String getUnit(int count) {
        if (count < 10000) {
            return "" + count;
        }
        if (count < 100000000) {
            return "" + String.format("%.1f", count * 1.0 / 10000) + "万";
        }
        return "" + String.format("%.1f", count * 1.0 / 100000000) + "亿";
    }

    public static void test() {
        TreeMap<String, String> tmap = new TreeMap<String, String>();
        tmap.put("cce", "3");

        tmap.put("abc", "2");
        tmap.put("ace", "3");
        TreeMap<String, String> tmap2=(TreeMap<String, String>) tmap.clone();
        System.out.println(tmap2.hashCode() + " " + tmap.hashCode());
        //对map利用key排序
        Map<String, String> resMap = sortMapByKey(tmap);
        for (Map.Entry<String, String> entry : resMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        //对map利用key排序

        for (Map.Entry<String, String> entry : tmap2.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }


    /**
     * 让 Map按key进行排序
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<String, String>(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
//                System.out.println(s1 + " " + s2 + " " + s1.compareTo(s2));
                return s1.toLowerCase().compareTo(s2.toLowerCase());  //从小到大排序
            }
        });
        sortMap.putAll(map);
        return sortMap;
    }


}
