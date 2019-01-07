package djjtest.com.androiddemo.view.timeselector;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.databinding.DialogSelectortimeBinding;
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

    public interface ResultHandler2 extends ResultHandler {
        void handle(Calendar calendar);
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


    final private ArrayList<String> year = new ArrayList<>();
    final private ArrayList<String> month = new ArrayList<>();
    final private ArrayList<String> day = new ArrayList<>();
    final private ArrayList<String> hour = new ArrayList<>();
    final private ArrayList<String> minute = new ArrayList<>();
    private Calendar selectedCalender = Calendar.getInstance();
    private final long ANIMATORDELAY = 200L;
    private final long CHANGEDELAY = 90L;
    final private Calendar startCalendar;
    final private Calendar endCalendar;


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


    boolean forwardTenYear = true;

    public TimeSelector setForwardTenYear(boolean var) {
        forwardTenYear = var;
        return this;
    }

    boolean justShowScrollUnit = false;

    public TimeSelector setJustShowScrollUnit(boolean var) {
        justShowScrollUnit = var;
        return this;
    }

    public void show() {
        if (forwardTenYear) {
            startCalendar.set(Calendar.YEAR, startCalendar.get(Calendar.YEAR) - 10);
        }
        if (currentCalendar == null) {
            currentCalendar = startCalendar;
        }

        selectedCalender = currentCalendar;
        if (startCalendar.getTime().getTime() > endCalendar.getTime().getTime()) {
            Toast.makeText(context, "起始时间应小于结束时间", Toast.LENGTH_LONG).show();
            return;
        }
        initTimer();
        addListener();
        initViewState();
        seletorDialog.show();
    }

    private void initDialog() {
        if (seletorDialog == null) {
            seletorDialog = new Dialog(context, R.style.time_dialog);
            seletorDialog.setCancelable(true);
            seletorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


            View view = LayoutInflater.from(context).inflate(R.layout.dialog_selectortime, null);
            binding = DataBindingUtil.bind(view);
            seletorDialog.setContentView(view);

            Window window = seletorDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = window.getAttributes();
            int width = ScreenUtil.getInstance(context).getScreenWidth();
            lp.width = width;
            window.setAttributes(lp);
        }
    }


    private DialogSelectortimeBinding binding;

    private void initView() {
        binding.yearPv.setData(year);
        binding.monthPv.setData(month);
        binding.dayPv.setData(day);
        binding.hourPv.setData(hour);
        binding.minutePv.setData(minute);
        binding.tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seletorDialog.dismiss();
            }
        });
        binding.tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.handle(DateUtil.format(selectedCalender.getTime(), FORMAT_STR));
                if (handler instanceof ResultHandler2) {
                    ((ResultHandler2) handler).handle(selectedCalender);
                }
                seletorDialog.dismiss();
            }
        });

    }

    private void initViewState() {
        if (!justShowScrollUnit) {
            return;
        }
        checkUnitShow(SCROLLTYPE.YEAR.value, binding.yearPv, binding.yearPvUnit);
        checkUnitShow(SCROLLTYPE.MONTH.value, binding.monthPv, binding.monthPvUnit);
        checkUnitShow(SCROLLTYPE.DAY.value, binding.dayPv, binding.dayPvUnit);
        checkUnitShow(SCROLLTYPE.HOUR.value, binding.hourPv, binding.hourPvUnit);
        checkUnitShow(SCROLLTYPE.MINUTE.value, binding.minutePv, binding.minutePvUnit);
    }

    private void checkUnitShow(int value, View... views) {
        int show = (scrollUnits & value) > 0 ? View.VISIBLE : View.GONE;
        for (View v : views) {
            v.setVisibility(show);
        }
    }


    /**
     * 数据源
     */
    private void initTimer() {
        CommonUtils.log("initTimer", selectedCalender);
        int startYear = startCalendar.get(Calendar.YEAR);
        int endYear = endCalendar.get(Calendar.YEAR);
        for (int i = startYear; i <= endYear; i++) {
            year.add(String.valueOf(i));
        }
        binding.yearPv.setSelected(year.indexOf(formatTimeUnit(currentCalendar.get(Calendar.YEAR))));
        binding.yearPv.setCanScroll(year.size() > 1 && (scrollUnits & SCROLLTYPE.YEAR.value) == SCROLLTYPE.YEAR.value);

        monthChange();

    }


    private String formatTimeUnit(int unit) {
        return unit < 10 ? "0" + String.valueOf(unit) : String.valueOf(unit);
    }


    private void addListener() {
        binding.yearPv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectedCalender.set(Calendar.YEAR, Integer.parseInt(text));
                monthChange();
            }
        });
        binding.monthPv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectedCalender.set(Calendar.MONTH, Integer.parseInt(text) - 1);
                dayChange();

            }
        });
        binding.dayPv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(text));
                hourChange();
            }
        });
        binding.hourPv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectedCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(text));
                minuteChange(); //放开为多级联动
            }
        });
        binding.minutePv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectedCalender.set(Calendar.MINUTE, Integer.parseInt(text));
            }
        });

    }


    private void excuteScroll() {
        binding.yearPv.setCanScroll(year.size() > 1 && (scrollUnits & SCROLLTYPE.YEAR.value) == SCROLLTYPE.YEAR.value);
        binding.monthPv.setCanScroll(month.size() > 1 && (scrollUnits & SCROLLTYPE.MONTH.value) == SCROLLTYPE.MONTH.value);
        binding.dayPv.setCanScroll(day.size() > 1 && (scrollUnits & SCROLLTYPE.DAY.value) == SCROLLTYPE.DAY.value);
        binding.hourPv.setCanScroll(hour.size() > 1 && (scrollUnits & SCROLLTYPE.HOUR.value) == SCROLLTYPE.HOUR.value);
        binding.minutePv.setCanScroll(minute.size() > 1 && (scrollUnits & SCROLLTYPE.MINUTE.value) == SCROLLTYPE.MINUTE.value);
    }

    private void monthChange() {
        month.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        int selectedMonth = selectedCalender.get(Calendar.MONTH);
        int beforeSelectedMonth = selectedMonth;
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
        CommonUtils.log("month:", getList(month));

        selectedMonth = Math.min(selectedMonth, endMonth);
        selectedMonth = Math.max(selectedMonth, startMonth);
        selectedCalender.set(Calendar.MONTH, selectedMonth);
        binding.monthPv.setData(month);
        binding.monthPv.setSelected(month.indexOf(formatTimeUnit(selectedMonth + 1)));
//        CommonUtils.log("selectedMonth", selectedMonth, "  ", formatTimeUnit(selectedMonth), "!!", month.indexOf(formatTimeUnit(selectedMonth)));
        if (beforeSelectedMonth != selectedMonth) {
            excuteAnimator(ANIMATORDELAY, binding.monthPv);
        }

        binding.monthPv.setCanScroll(month.size() > 1 && (scrollUnits & SCROLLTYPE.MONTH.value) == SCROLLTYPE.MONTH.value);
        dayChange();
    }

    private String getList(ArrayList list) {
        if (list == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        for (Object o : list) {
            if (o == null) {
                sb.append("null,");
            } else {
                sb.append(o.toString()).append(",");
            }
        }
        return sb.toString();
    }

    /**
     * 多级联动 日
     */
    private void dayChange() {
        day.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        int selectedMonth = selectedCalender.get(Calendar.MONTH);
        int selectedDay = selectedCalender.get(Calendar.DAY_OF_MONTH);
        int beforeSelectedDay = selectedDay;
        int theStartDay = 1;
        int theEndDay = selectedCalender.getActualMaximum(Calendar.DATE);
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
        CommonUtils.log("day:", getList(day));
        CommonUtils.log("selectedMonth", selectedMonth, "  selectedDay", selectedDay, "theEndDay", theEndDay);
        selectedDay = Math.max(selectedDay, theStartDay);
        selectedDay = Math.min(selectedDay, theEndDay);
//        CommonUtils.log("selectedDay", selectedDay, "  theStartDay", theStartDay, "theEndDay", theEndDay);

        selectedCalender.set(Calendar.DAY_OF_MONTH, selectedDay);
        binding.dayPv.setData(day);
        binding.dayPv.setSelected(day.indexOf(formatTimeUnit(selectedDay)));
        if (beforeSelectedDay != selectedDay) {

            excuteAnimator(ANIMATORDELAY, binding.dayPv);
        }

        binding.dayPv.setCanScroll(day.size() > 1 && (scrollUnits & SCROLLTYPE.DAY.value) == SCROLLTYPE.DAY.value);

        hourChange();

    }

    /**
     * 多级联动 小时
     */
    private void hourChange() {
        hour.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        int selectedMonth = selectedCalender.get(Calendar.MONTH);
        int selectedDay = selectedCalender.get(Calendar.DAY_OF_MONTH);
        int selectedHour = selectedCalender.get(Calendar.HOUR_OF_DAY);
        int beforeSelectedHour = selectedHour;
        int theStartHour = 0;
        int theEndHour = 23;
        if (selectedYear == startCalendar.get(Calendar.YEAR)
                && selectedMonth == startCalendar.get(Calendar.MONTH)
                && selectedDay == startCalendar.get(Calendar.DAY_OF_MONTH)) {
            theStartHour = startCalendar.get(Calendar.HOUR_OF_DAY);
        }
        if (selectedYear == endCalendar.get(Calendar.YEAR)
                && selectedMonth == endCalendar.get(Calendar.MONTH)
                && selectedDay == endCalendar.get(Calendar.DAY_OF_MONTH)) {
            theEndHour = endCalendar.get(Calendar.HOUR_OF_DAY);
        }
        for (int i = theStartHour; i <= theEndHour; i++) {
            hour.add(formatTimeUnit(i));
        }
        CommonUtils.log("hour:", getList(hour), "selectedHour", selectedHour, "theStartHour", theStartHour, "theEndHour", theEndHour);
        selectedHour = Math.max(selectedHour, theStartHour);
        selectedHour = Math.min(selectedHour, theEndHour);
        selectedCalender.set(Calendar.HOUR_OF_DAY, selectedHour);
        binding.hourPv.setData(hour);
        binding.hourPv.setSelected(hour.indexOf(formatTimeUnit(selectedHour)));
        if (beforeSelectedHour != selectedHour) {
            excuteAnimator(ANIMATORDELAY, binding.hourPv);
        }

        binding.hourPv.setCanScroll(hour.size() > 1 && (scrollUnits & SCROLLTYPE.HOUR.value) == SCROLLTYPE.HOUR.value);

        minuteChange();
    }


    private void minuteChange() {
        minute.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        int selectedMonth = selectedCalender.get(Calendar.MONTH);
        int selectedDay = selectedCalender.get(Calendar.DAY_OF_MONTH);
        int selectedHour = selectedCalender.get(Calendar.HOUR_OF_DAY);
        int selectedMinute = selectedCalender.get(Calendar.MINUTE);
        int beforeSelectedMinute = selectedMinute;
        int theStartMinute = 0;
        int theEndMinute = 59;
        if (selectedYear == startCalendar.get(Calendar.YEAR)
                && selectedMonth == startCalendar.get(Calendar.MONTH)
                && selectedDay == startCalendar.get(Calendar.DAY_OF_MONTH)
                && selectedHour == startCalendar.get(Calendar.HOUR_OF_DAY)) {
            theStartMinute = startCalendar.get(Calendar.MINUTE);
        }
        if (selectedYear == endCalendar.get(Calendar.YEAR)
                && selectedMonth == endCalendar.get(Calendar.MONTH)
                && selectedDay == endCalendar.get(Calendar.DAY_OF_MONTH)
                && selectedHour == endCalendar.get(Calendar.HOUR_OF_DAY)) {
            theEndMinute = endCalendar.get(Calendar.MINUTE);
        }

        for (int i = theStartMinute; i <= theEndMinute; i++) {
            minute.add(formatTimeUnit(i));
        }
        CommonUtils.log("selectedCalender:", selectedCalender);
        CommonUtils.log("endCalendar:", endCalendar);

        CommonUtils.log("minute:", getList(minute), "theStartMinute", theStartMinute, "theEndMinute", theEndMinute);
        selectedMinute = Math.max(theStartMinute, selectedMinute);
        selectedMinute = Math.min(theEndMinute, selectedMinute);

        selectedCalender.set(Calendar.MINUTE, selectedMinute);
        binding.minutePv.setData(minute);
        binding.minutePv.setSelected(minute.indexOf(formatTimeUnit(selectedMinute)));
        if (beforeSelectedMinute != selectedMinute) {
            excuteAnimator(ANIMATORDELAY, binding.minutePv);
        }

        binding.minutePv.setCanScroll(minute.size() > 1 && (scrollUnits & SCROLLTYPE.MINUTE.value) == SCROLLTYPE.MINUTE.value);
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
        binding.tvSelect.setText(str);
    }


    public TimeSelector setScrollUnit(SCROLLTYPE... scrolltypes) {
        scrollUnits = 0;
        for (SCROLLTYPE scrolltype : scrolltypes) {
            scrollUnits ^= scrolltype.value;
        }
        return this;
    }

}
