package djjtest.com.androiddemo.view;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import djjtest.com.androiddemo.FragmentAdapter;
import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.databinding.TestFragmentBinding;
import djjtest.com.androiddemo.utils.CommonUtils;
import djjtest.com.androiddemo.view.timeselector.DateUtil;
import djjtest.com.androiddemo.view.timeselector.TimeSelector;

/**
 * Author      :    DongJunJie
 * Date        :    2018/12/18
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class TestFragment extends FragmentAdapter.BaseFragment {

    private int layout_id = R.layout.test_fragment;
    TestFragmentBinding binding;


    @Override
    public CharSequence getTitle() {
        return "测试";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(layout_id, container, false);
        binding = DataBindingUtil.bind(v);
        initView();
        return v;
    }

    private void initView() {
        binding.right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String dataString = formatter.format(curDate);
                TimeSelector timeSelectorDialog = new TimeSelector(getActivity(), new TimeSelector.ResultHandler2() {
                    @Override
                    public void handle(Calendar calendar) {
                        String time = DateUtil.format(calendar.getTime(), "yyyy-MM-dd");
                        CommonUtils.log("time2", time);
                    }

                    @Override
                    public void handle(String time) {
                        CommonUtils.log("time1", time);
                    }
                }, "2010-11-10 06:08", "2012-1-7 8:24").setCurrentTime("2012-1-7 8:24");
                timeSelectorDialog.setScrollUnit(TimeSelector.SCROLLTYPE.YEAR,
                        TimeSelector.SCROLLTYPE.MONTH,
                        TimeSelector.SCROLLTYPE.DAY)
                        .setJustShowScrollUnit(true);
                timeSelectorDialog.show();
            }
        });

        //创建一个SpannableString对象
        SpannableString sStr = new SpannableString("现金折扣7sdfsdf折");
        sStr.setSpan(new AbsoluteSizeSpan(15, true), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new ForegroundColorSpan(0xff999999), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new AbsoluteSizeSpan(18, true), 4, sStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new ForegroundColorSpan(0xffff7f2c), 4, sStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.textView.setText(sStr);
    }
}
