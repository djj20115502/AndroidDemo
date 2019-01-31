package djjtest.com.androiddemo.view.timeselector;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Author      :    DongJunJie
 * Date        :    2018/12/18
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class DateUtil {
    public final static String STR_FORMAT_YYYY_MM_DD="yyyy-MM-dd";
    public final static String STR_FORMAT_NORMAL="yyyy-MM-dd hh:mm:ss";

    public final static SimpleDateFormat FORMAT_YYYY_MM_DD=new SimpleDateFormat(STR_FORMAT_YYYY_MM_DD, Locale.CHINA);
    public final static SimpleDateFormat FORMAT_NORMAL=new SimpleDateFormat(STR_FORMAT_NORMAL, Locale.CHINA);

    public final static int SECOND=1000;
    public final static int MINUTE=60*SECOND;
    public final static int HOUR=60*MINUTE;
    public final static int DAY=24*HOUR;

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */

    public static Date parse(String strDate, String pattern) {

        if (TextUtils.isEmpty(strDate)) {
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long parseToTimeMill(String strDate,String pattern){
        Date date=parse(strDate,pattern);
        if(date!=null)return date.getTime();
        return 0;
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */

    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }


    public static long getResidueTimeMill(String date){
        try {
            long timeMill=FORMAT_NORMAL.parse(date).getTime();
            return timeMill-System.currentTimeMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getResidueTimeStr(String date){
        long residue=getResidueTimeMill(date);
        int s=(int)(residue%MINUTE)/SECOND;
        int m=(int)(residue% HOUR)/MINUTE;
        int h=(int)(residue% DAY)/HOUR;
        int d=(int)residue/ DAY;
        StringBuilder sb=new StringBuilder();
        if(d>0){
            sb.append(d).append("天");
        }
        if(h>0){
            sb.append(h).append("小时");
        }
        if(m>0){
            sb.append(m).append("分");
        }
        if(s>0){
            sb.append(s).append("秒");
        }
        return sb.toString();
    }

    /**
     *
     * @param year
     * @param month
     * @param day
     * @return  yyyy-MM-dd hh:mm:ss
     */
    public static String format(int year,int month,int day){
        Calendar calendar=Calendar.getInstance(Locale.CHINA);
        calendar.set(year,month,day,0,0,0);
        return format(calendar.getTime(),STR_FORMAT_NORMAL);
    }

    public static String getTodaySimpleFormat(){
        Calendar calendar=Calendar.getInstance(Locale.CHINA);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),0,0,0);
        return format(calendar.getTime(),STR_FORMAT_NORMAL);
    }
}
