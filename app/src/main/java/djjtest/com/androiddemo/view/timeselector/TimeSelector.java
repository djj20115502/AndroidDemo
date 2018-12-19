package djjtest.com.androiddemo.view.timeselector;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.utils.CommonUtils;

/**
 * Author      :    DongJunJie
 * Date        :    2018/12/18
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class TimeSelector {
    public interface ResultHandler {
        void handle(String time);
    }

    public enum SCROLLTYPE {
        YEAR(1),
        MONTH(2),
        DAY(4),
        HOUR(8),
        MINUTE(16);

        SCROLLTYPE(int value) {
            this.value = value;
        }

        public int value;

    }

    private int scrollUnits = SCROLLTYPE.YEAR.value + SCROLLTYPE.MONTH.value + SCROLLTYPE.DAY.value + SCROLLTYPE.HOUR.value + SCROLLTYPE.MINUTE.value;
    private ResultHandler handler;
    private Context context;
    private final String FORMAT_STR = "yyyy-MM-dd HH:mm";

    private Dialog seletorDialog;
    private PickerView year_pv;
    private PickerView month_pv;
    private PickerView day_pv;
    private PickerView hour_pv;
    private PickerView minute_pv;

    private final int MAXMINUTE = 59;
    private int MAXHOUR = 23;
    private final int MINMINUTE = 0;
    private int MINHOUR = 0;
    private final int MAXMONTH = 12;

    private ArrayList<String> year, month, day, hour, minute;
    private int startYear, startMonth, startDay, startHour, startMininute, endYear, endMonth, endDay, endHour, endMininute, minute_workStart, minute_workEnd, hour_workStart, hour_workEnd;
    private boolean spanYear, spanMon, spanDay, spanHour, spanMin;
    private Calendar selectedCalender = Calendar.getInstance();
    private final long ANIMATORDELAY = 200L;
    private final long CHANGEDELAY = 90L;
    private String workStart_str;
    private String workEnd_str;
    private Calendar startCalendar;
    private Calendar endCalendar;
    private TextView tv_cancle;
    private TextView tv_select;

    /**
     * @param context
     * @param resultHandler 选取时间后回调
     * @param startDate     format："yyyy-MM-dd HH:mm"
     * @param endDate
     */
    public TimeSelector(Context context, ResultHandler resultHandler, String startDate, String endDate) {
        this.context = context;
        this.handler = resultHandler;
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        startCalendar.setTime(DateUtil.parse(startDate, FORMAT_STR));
        endCalendar.setTime(DateUtil.parse(endDate, FORMAT_STR));
        initDialog();
        initView();
    }

    private Calendar currentCalendar;

    public TimeSelector setCurrentTime(String currentTime) {
        currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(DateUtil.parse(currentTime, FORMAT_STR));
        return this;
    }

    /**
     * @param context
     * @param startDate
     * @param endDate
     * @param workStartTime 工作日起始时间 如：朝九晚五  format："09:00"
     * @param workEndTime   工作日结束时间  format："17:00"
     */
    public TimeSelector(Context context, ResultHandler resultHandler, String startDate, String endDate, String workStartTime, String workEndTime) {
        this(context, resultHandler, startDate, endDate);
        this.workStart_str = workStartTime;
        this.workEnd_str = workEndTime;
    }

    public void show() {
        if (startCalendar.getTime().getTime() > endCalendar.getTime().getTime()) {
            Toast.makeText(context, "起始时间应小于结束时间", Toast.LENGTH_LONG).show();
            return;
        }

        if (!excuteWorkTime()) return;
        initParameter();
        initTimer();
        addListener();
        seletorDialog.show();
    }

    private void initDialog() {
        if (seletorDialog == null) {
            seletorDialog = new Dialog(context, R.style.time_dialog);
            seletorDialog.setCancelable(true);
            seletorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            seletorDialog.setContentView(R.layout.dialog_selectortime);
            Window window = seletorDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = window.getAttributes();
            int width = ScreenUtil.getInstance(context).getScreenWidth();
            lp.width = width;
            window.setAttributes(lp);
        }
    }

    private void initView() {
        year_pv = seletorDialog.findViewById(R.id.year_pv);
        month_pv = seletorDialog.findViewById(R.id.month_pv);
        day_pv = seletorDialog.findViewById(R.id.day_pv);
        hour_pv = seletorDialog.findViewById(R.id.hour_pv);
        minute_pv = seletorDialog.findViewById(R.id.minute_pv);
        tv_cancle = seletorDialog.findViewById(R.id.tv_cancle);
        tv_select = seletorDialog.findViewById(R.id.tv_select);

        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seletorDialog.dismiss();
            }
        });
        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.handle(DateUtil.format(selectedCalender.getTime(), FORMAT_STR));
                seletorDialog.dismiss();
            }
        });

    }

    /**
     * 设置开始结束的年月日时分
     * 月份+1
     */
    private void initParameter() {
        startYear = startCalendar.get(Calendar.YEAR);
        startMonth = startCalendar.get(Calendar.MONTH) + 1;
        startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
        startHour = startCalendar.get(Calendar.HOUR_OF_DAY);
        startMininute = startCalendar.get(Calendar.MINUTE);
        endYear = endCalendar.get(Calendar.YEAR);
        endMonth = endCalendar.get(Calendar.MONTH) + 1;
        endDay = endCalendar.get(Calendar.DAY_OF_MONTH);
        endHour = endCalendar.get(Calendar.HOUR_OF_DAY);
        endMininute = endCalendar.get(Calendar.MINUTE);
        spanYear = startYear != endYear;
        spanMon = (!spanYear) && (startMonth != endMonth);
        spanDay = (!spanMon) && (startDay != endDay);
        spanHour = (!spanDay) && (startHour != endHour);
        spanMin = (!spanHour) && (startMininute != endMininute);
    }

    /**
     * 数据源
     */
    private void initTimer() {
        initArrayList();

        if (spanYear) {
            for (int i = startYear; i <= endYear; i++) {
                year.add(String.valueOf(i));
            }
            for (int i = 1; i <= MAXMONTH; i++) {
                month.add(formatTimeUnit(i));
            }
            for (int i = 1; i <= startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(formatTimeUnit(i));
            }
            for (int i = 0; i <= MAXHOUR; i++) {
                hour.add(formatTimeUnit(i));
            }
            for (int i = 0; i <= MAXMINUTE; i++) {
                minute.add(formatTimeUnit(i));
            }
        } else if (spanMon) {
            year.add(String.valueOf(startYear));
            for (int i = 1; i <= endMonth; i++) {
                month.add(formatTimeUnit(i));
            }
            for (int i = 0; i <= startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(formatTimeUnit(i));
            }
            for (int i = 0; i <= MAXHOUR; i++) {
                hour.add(formatTimeUnit(i));
            }
            for (int i = 0; i <= MAXMINUTE; i++) {
                minute.add(formatTimeUnit(i));
            }
        } else if (spanDay) {
            year.add(String.valueOf(startYear));
            month.add(formatTimeUnit(startMonth));
            for (int i = 1; i <= endDay; i++) {
                day.add(formatTimeUnit(i));
            }
            for (int i = 0; i <= MAXHOUR; i++) {
                hour.add(formatTimeUnit(i));
            }
            for (int i = 0; i <= MAXMINUTE; i++) {
                minute.add(formatTimeUnit(i));
            }

        } else if (spanHour) {
            year.add(String.valueOf(startYear));
            month.add(formatTimeUnit(startMonth));
            day.add(formatTimeUnit(startDay));
            for (int i = 0; i <= endHour; i++) {
                hour.add(formatTimeUnit(i));
            }
            for (int i = 0; i <= MAXMINUTE; i++) {
                minute.add(formatTimeUnit(i));
            }

        } else if (spanMin) {
            year.add(String.valueOf(startYear));
            month.add(formatTimeUnit(startMonth));
            day.add(formatTimeUnit(startDay));
            hour.add(formatTimeUnit(startHour));
            for (int i = 0; i <= endMininute; i++) {
                minute.add(formatTimeUnit(i));
            }
        } else {
            year.add(String.valueOf(startYear));
            month.add(formatTimeUnit(startMonth));
            day.add(formatTimeUnit(startDay));
            hour.add(formatTimeUnit(startHour));
            minute.add(formatTimeUnit(startMininute));
        }
        loadComponent();

    }

    private boolean excuteWorkTime() {
        boolean res = true;
        if (!TextUtils.isEmpty(workStart_str) && !TextUtils.isEmpty(workEnd_str)) {
            String[] start = workStart_str.split(":");
            String[] end = workEnd_str.split(":");
            hour_workStart = Integer.parseInt(start[0]);
            minute_workStart = Integer.parseInt(start[1]);
            hour_workEnd = Integer.parseInt(end[0]);
            minute_workEnd = Integer.parseInt(end[1]);
            Calendar workStartCalendar = Calendar.getInstance();
            Calendar workEndCalendar = Calendar.getInstance();
            workStartCalendar.setTime(startCalendar.getTime());
            workEndCalendar.setTime(endCalendar.getTime());
            workStartCalendar.set(Calendar.HOUR_OF_DAY, hour_workStart);
            workStartCalendar.set(Calendar.MINUTE, minute_workStart);
            workEndCalendar.set(Calendar.HOUR_OF_DAY, hour_workEnd);
            workEndCalendar.set(Calendar.MINUTE, minute_workEnd);


            Calendar startTime = Calendar.getInstance();
            Calendar endTime = Calendar.getInstance();
            Calendar startWorkTime = Calendar.getInstance();
            Calendar endWorkTime = Calendar.getInstance();

            startTime.set(Calendar.HOUR_OF_DAY, startCalendar.get(Calendar.HOUR_OF_DAY));
            startTime.set(Calendar.MINUTE, startCalendar.get(Calendar.MINUTE));
            endTime.set(Calendar.HOUR_OF_DAY, endCalendar.get(Calendar.HOUR_OF_DAY));
            endTime.set(Calendar.MINUTE, endCalendar.get(Calendar.MINUTE));

            startWorkTime.set(Calendar.HOUR_OF_DAY, workStartCalendar.get(Calendar.HOUR_OF_DAY));
            startWorkTime.set(Calendar.MINUTE, workStartCalendar.get(Calendar.MINUTE));
            endWorkTime.set(Calendar.HOUR_OF_DAY, workEndCalendar.get(Calendar.HOUR_OF_DAY));
            endWorkTime.set(Calendar.MINUTE, workEndCalendar.get(Calendar.MINUTE));


            if (startTime.getTime().getTime() == endTime.getTime().getTime() || (startWorkTime.getTime().getTime() < startTime.getTime().getTime() && endWorkTime.getTime().getTime() < startTime.getTime().getTime())) {
                Toast.makeText(context, "时间参数错误", Toast.LENGTH_LONG).show();
                return false;
            }
            startCalendar.setTime(startCalendar.getTime().getTime() < workStartCalendar.getTime().getTime() ? workStartCalendar.getTime() : startCalendar.getTime());
            endCalendar.setTime(endCalendar.getTime().getTime() > workEndCalendar.getTime().getTime() ? workEndCalendar.getTime() : endCalendar.getTime());
            MINHOUR = workStartCalendar.get(Calendar.HOUR_OF_DAY);
            MAXHOUR = workEndCalendar.get(Calendar.HOUR_OF_DAY);

        }
        return res;


    }

    private String formatTimeUnit(int unit) {
        return unit < 10 ? "0" + String.valueOf(unit) : String.valueOf(unit);
    }

    private void initArrayList() {
        if (year == null) year = new ArrayList<String>();
        if (month == null) month = new ArrayList<String>();
        if (day == null) day = new ArrayList<String>();
        if (hour == null) hour = new ArrayList<String>();
        if (minute == null) minute = new ArrayList<String>();
        year.clear();
        month.clear();
        day.clear();
        hour.clear();
        minute.clear();
    }


    private void addListener() {
        year_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectedCalender.set(Calendar.YEAR, Integer.parseInt(text));
                monthChange();  //放开为多级联动
            }
        });
        month_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectedCalender.set(Calendar.MONTH, Integer.parseInt(text) - 1);
                dayChange(); //放开为多级联动


            }
        });
        day_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(text));
                hourChange(); //放开为多级联动

            }
        });
        hour_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectedCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(text));
                minuteChange(); //放开为多级联动


            }
        });
        minute_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectedCalender.set(Calendar.MINUTE, Integer.parseInt(text));


            }
        });

    }

    /**
     * 设置值
     */
    private void loadComponent() {
        year_pv.setData(year);
        month_pv.setData(month);
        day_pv.setData(day);
        hour_pv.setData(hour);
        minute_pv.setData(minute);
        if (currentCalendar == null) {
            currentCalendar = startCalendar;
        }
        year_pv.setSelected(currentCalendar.get(Calendar.YEAR) - startYear);
        month_pv.setSelected(currentCalendar.get(Calendar.MONTH));
        day_pv.setSelected(currentCalendar.get(Calendar.DAY_OF_MONTH) - 1);
        hour_pv.setSelected(currentCalendar.get(Calendar.HOUR_OF_DAY));
        minute_pv.setSelected(currentCalendar.get(Calendar.MINUTE));
        selectedCalender.setTime(currentCalendar.getTime());
        excuteScroll();
    }

    private void excuteScroll() {
        year_pv.setCanScroll(year.size() > 1 && (scrollUnits & SCROLLTYPE.YEAR.value) == SCROLLTYPE.YEAR.value);
        month_pv.setCanScroll(month.size() > 1 && (scrollUnits & SCROLLTYPE.MONTH.value) == SCROLLTYPE.MONTH.value);
        day_pv.setCanScroll(day.size() > 1 && (scrollUnits & SCROLLTYPE.DAY.value) == SCROLLTYPE.DAY.value);
        hour_pv.setCanScroll(hour.size() > 1 && (scrollUnits & SCROLLTYPE.HOUR.value) == SCROLLTYPE.HOUR.value);
        minute_pv.setCanScroll(minute.size() > 1 && (scrollUnits & SCROLLTYPE.MINUTE.value) == SCROLLTYPE.MINUTE.value);
    }

    /**
     * 多级联动 月份
     */
    private void monthChange() {
        month.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        int selectedMonth = selectedCalender.get(Calendar.MONTH);
        int startMonth = Calendar.JANUARY;
        int endMonth = Calendar.DECEMBER;
        if (selectedYear == startCalendar.get(Calendar.YEAR)) {
            startMonth = startCalendar.get(Calendar.MONTH);
        }
        if (selectedYear == endCalendar.get(Calendar.YEAR)) {
            endMonth = endCalendar.get(Calendar.MONTH);
        }
        //由于是0-11 显示的时候这里要切换到1-12
        for (int i = startMonth; i <= endMonth; i++) {
            month.add(formatTimeUnit(i + 1));
        }
        selectedMonth = Math.min(selectedMonth, endMonth);
        selectedMonth = Math.max(selectedMonth, startMonth);
        selectedCalender.set(Calendar.MONTH, selectedMonth);
        month_pv.setData(month);
        month_pv.setSelected(month.indexOf(formatTimeUnit(selectedMonth + 1)));
        CommonUtils.log("selectedMonth", selectedMonth, "  ", formatTimeUnit(selectedMonth), "!!", month.indexOf(formatTimeUnit(selectedMonth)));
        excuteAnimator(ANIMATORDELAY, month_pv);

    }

    /**
     * 多级联动 日
     */
    private void dayChange() {
        day.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        int selectedMonth = selectedCalender.get(Calendar.MONTH);
        int selectedDay = selectedCalender.get(Calendar.DAY_OF_MONTH);
        int theStartDay = 1;
        int theEndDay = selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (selectedYear == startCalendar.get(Calendar.YEAR)
                && selectedMonth == startCalendar.get(Calendar.MONTH)) {
            theStartDay = startCalendar.get(Calendar.DAY_OF_MONTH);
        }
        if (selectedYear == endCalendar.get(Calendar.YEAR)
                && selectedMonth == endCalendar.get(Calendar.MONTH)) {
            theEndDay = endCalendar.get(Calendar.DAY_OF_MONTH);
        }
        for (int i = theStartDay; i <= theEndDay; i++) {
            day.add(formatTimeUnit(i));
        }
        selectedDay = Math.min(selectedDay, theStartDay);
        selectedDay = Math.max(selectedDay, theEndDay);
        selectedCalender.set(Calendar.DAY_OF_MONTH, selectedDay);
        day_pv.setData(day);
        day_pv.setSelected(day.indexOf(formatTimeUnit(selectedDay)));
        excuteAnimator(ANIMATORDELAY, day_pv);
    }

    /**
     * 多级联动 小时
     */
    private void hourChange() {
        hour.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        int selectedMonth = selectedCalender.get(Calendar.MONTH) + 1;
        int selectedDay = selectedCalender.get(Calendar.DAY_OF_MONTH);


        if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay) {
            for (int i = startHour; i <= MAXHOUR; i++) {
                hour.add(formatTimeUnit(i));
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay) {
            for (int i = MINHOUR; i <= endHour; i++) {
                hour.add(formatTimeUnit(i));
            }
        } else {

            for (int i = MINHOUR; i <= MAXHOUR; i++) {
                hour.add(formatTimeUnit(i));
            }

        }
        selectedCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour.get(0)));
        hour_pv.setData(hour);
        hour_pv.setSelected(0);
        excuteAnimator(ANIMATORDELAY, hour_pv);
        hour_pv.postDelayed(new Runnable() {
            @Override
            public void run() {
                // minuteChange();
            }
        }, CHANGEDELAY);

    }


    private void minuteChange() {
        minute.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        int selectedMonth = selectedCalender.get(Calendar.MONTH) + 1;
        int selectedDay = selectedCalender.get(Calendar.DAY_OF_MONTH);
        int selectedHour = selectedCalender.get(Calendar.HOUR_OF_DAY);

        if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay && selectedHour == startHour) {
            for (int i = startMininute; i <= MAXMINUTE; i++) {
                minute.add(formatTimeUnit(i));
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay && selectedHour == endHour) {
            for (int i = MINMINUTE; i <= endMininute; i++) {
                minute.add(formatTimeUnit(i));
            }
        } else if (selectedHour == hour_workStart) {
            for (int i = minute_workStart; i <= MAXMINUTE; i++) {
                minute.add(formatTimeUnit(i));
            }
        } else if (selectedHour == hour_workEnd) {
            for (int i = MINMINUTE; i <= minute_workEnd; i++) {
                minute.add(formatTimeUnit(i));
            }
        } else {
            for (int i = MINMINUTE; i <= MAXMINUTE; i++) {
                minute.add(formatTimeUnit(i));
            }
        }
        selectedCalender.set(Calendar.MINUTE, Integer.parseInt(minute.get(0)));
        minute_pv.setData(minute);
        minute_pv.setSelected(0);
        excuteAnimator(ANIMATORDELAY, minute_pv);
        excuteScroll();


    }

    private void excuteAnimator(long ANIMATORDELAY, View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                1.3f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                1.3f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(ANIMATORDELAY).start();
    }

    /**
     * 设置选取时间文本 默认"选择"
     */
    public void setNextBtTip(String str) {
        tv_select.setText(str);
    }


    public TimeSelector setScrollUnit(SCROLLTYPE... scrolltypes) {
        scrollUnits = 0;
        for (SCROLLTYPE scrolltype : scrolltypes) {
            scrollUnits ^= scrolltype.value;
        }
        return this;
    }

}
