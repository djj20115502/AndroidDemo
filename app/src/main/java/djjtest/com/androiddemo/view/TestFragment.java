package djjtest.com.androiddemo.view;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import djjtest.com.androiddemo.FragmentAdapter;
import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.databinding.TestFragmentBinding;
import djjtest.com.androiddemo.utils.CommonUtils;
import djjtest.com.androiddemo.utils.TextCenterFormat;
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

                onClickClass();

            }
        });

        //创建一个SpannableString对象
        SpannableString sStr = new SpannableString("现金折扣7sdfsdf折");
        sStr.setSpan(new StrikethroughSpan(), 5,  9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new AbsoluteSizeSpan(15, true), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new ForegroundColorSpan(0xff999999), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new AbsoluteSizeSpan(18, true), 4, sStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new ForegroundColorSpan(0xffff7f2c), 4, sStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);



        binding.textView.setText(sStr );
        binding.textView2.setText(TextCenterFormat.formatText("服服服",4));
        binding.textView3.setText(TextCenterFormat.formatText("服服",4));
        binding.textView4.setText(TextCenterFormat.formatText("服服服服",4));
        binding.textView5.setText(TextCenterFormat.formatText("服服服服服服",4));
        binding.homeEtValidate.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(".") && dest.toString().length() == 0) {
                    return "0.";
                }
                if (dest.toString().contains(".")) {
                    int index = dest.toString().indexOf(".");
                    int length = dest.toString().substring(index).length();
                    if (length == 3) {
                        return "";
                    }
                }
                return null;
            }
        }});
//        binding.testDataBind.setData(new Bean("测试","http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg",1));
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.testDataBind.setData(new Bean("测试", "http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg", 1, true));
    }

    CustomOnTextChooseDialog customOnTextChooseDialog;

    public void onClickClass() {
        ArrayList<String> types = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            types.add("手机充值" + i);
        }
        if (types == null) {
            return;
        }
        if (customOnTextChooseDialog == null) {
            customOnTextChooseDialog = new CustomOnTextChooseDialog.Builder()
                    .activity(getActivity())
                    .callBack(new CustomOnTextChooseDialog.CallBack() {
                        @Override
                        public void OnClick(Object data) {
                            CommonUtils.log(data);
                        }
                    })
                    .list(types)
                    .maxHeight(CommonUtils.dp2px(getActivity().getResources(), 100))
                    .width(CommonUtils.dp2px(getActivity().getResources(), 70))
                    .height(-2)
                    .build();
        }
        customOnTextChooseDialog.showAtAnchorView(binding.homeEtValidate, YGravity.BELOW, XGravity.ALIGN_RIGHT, 0, 0);
    }

    public void clickSimpleDateFormat() {
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
}
