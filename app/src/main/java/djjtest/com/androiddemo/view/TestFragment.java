package djjtest.com.androiddemo.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
                }, "2010-11-10 00:00", "2012-10-20 00:00").setCurrentTime("2011-5-4 00:00");
                timeSelectorDialog.setScrollUnit(TimeSelector.SCROLLTYPE.YEAR,
                        TimeSelector.SCROLLTYPE.MONTH,
                        TimeSelector.SCROLLTYPE.DAY);
                timeSelectorDialog.show();
            }
        });
    }
}
