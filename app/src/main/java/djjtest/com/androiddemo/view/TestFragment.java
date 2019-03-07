package djjtest.com.androiddemo.view;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.network.StateCallBack;
import com.example.network.Test;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import djjtest.com.androiddemo.FragmentAdapter;
import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.databinding.TestFragmentBinding;
import djjtest.com.androiddemo.utils.CommonUtils;
import djjtest.com.androiddemo.utils.IdCardUtil;
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
        binding.setHolder(this);
        initView();
        binding.homeEtValidate.setFilters(CommonUtils.getIDcardInputFilter(binding.homeEtValidate));
        binding.textView001.setSelected(true);
        return v;
    }

    private void initView() {
        binding.right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onClickClass2();
////                onClickClass();
                clickSimpleDateFormat();

            }
        });

        //创建一个SpannableString对象
        SpannableString sStr = new SpannableString("现金折扣7sdfsdf折");
        sStr.setSpan(new StrikethroughSpan(), 5, 9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new AbsoluteSizeSpan(15, true), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new ForegroundColorSpan(0xff999999), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new AbsoluteSizeSpan(18, true), 4, sStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new ForegroundColorSpan(0xffff7f2c), 4, sStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        binding.textView.setText(getSpannableString("现金折扣7sdfsdf折", "sdfsdf", new AbsoluteSizeSpan(50, true), new ForegroundColorSpan(0xffff7f2c)));

        String tt = "转发参与活动的路书至您的微信朋友圈、QQ、微博等，邀请好友进驻看车玩车并在路书中打卡，则邀请人将获得平台发放的现金奖金鼓励。" +
                "\n赏金为随机金额，通常邀约人打卡路书越多、打卡路线越完整，邀请人获得的赏金越高；赏金可提现，每月1日可进行提现操作。" +
                "\n本活动本者先到先得的原则，平台发放赏金超过单日赏金预算，当日赏金停止发放；超过当期活动总预算，当期活动自动截止。";

//        binding.textView0.setLineSpacing(20, 1);
//        setParagraphSpacing(binding.textView0, tt, 60);
//        //创建一个SpannableString对象
//        String s1 = "33.5万";
//        String S2 = "累计" + s1 + "人参与活动";
//        int delete = 0;
//        if (s1.contains("万") || s1.contains("亿")) {
//            delete = 1;
//        }
//        SpannableString sStr2 = new SpannableString(S2);
//        sStr2.setSpan(new ForegroundColorSpan(0xffce2e0d), 0, S2.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        sStr2.setSpan(new AbsoluteSizeSpan(12, true), 0, S2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        sStr2.setSpan(new AbsoluteSizeSpan(15, true), 2, 2 + s1.length() - delete, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        sStr2.setSpan(new StyleSpan(Typeface.BOLD), 2, 2 + s1.length() - delete, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        binding.textView0.setText(sStr2);


        binding.textView2.setText(TextCenterFormat.formatText("服服服", 4));
        binding.textView3.setText(TextCenterFormat.formatText("服服", 4));
        binding.textView4.setText(TextCenterFormat.formatText("服服服服", 4));
        binding.textView5.setText(TextCenterFormat.formatText("服服服服服服", 4));

        binding.textView5.setText("sdfsdfs");
        binding.textView5.post(new Runnable() {
            @Override
            public void run() {
                int ellipsisCount = binding.textView5.getLayout().getEllipsisCount(binding.textView5.getLineCount() - 1);
                CommonUtils.log("ellipsisCount", ellipsisCount);
            }
        });
        binding.homeEtValidate.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(".") && dest.toString().length() == 0) {
                    return "0.";
                }
                if (dest.toString().contains(".")) {
                    int index = dest.toString().indexOf(".");
                    int length = dest.toString().substring(index).length();
                    if (length == 2) {
                        return "";
                    }
                }
                return null;
            }
        }});
//        binding.testDataBind.setData(new Bean("测试","http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg",1));
    }


    /**
     * 针对单个字符串进行变化
     *
     * @param all    全部字符
     * @param change 需要改变的字符
     * @param spans  各种span{@link ForegroundColorSpan }{@link AbsoluteSizeSpan}{@link StrikethroughSpan}
     */
    public static SpannableString getSpannableString(CharSequence all, CharSequence change, Object... spans) {
        SpannableString spannableString = new SpannableString(all);
        int index = all.toString().indexOf(change.toString());
        int end = index + change.length();
        for (Object o : spans) {
            spannableString.setSpan(o, index, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannableString;

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

    public void onClickClass2() {

//        Intent intent=new Intent(getActivity(),TA.class);
//        getActivity().startActivity(intent);
        new ScanQrCodeBuilder().makeWindow(getActivity()).show();
//           NotifyDialog.Builder(getActivity()).build().show();
    }

    public void clickSimpleDateFormat() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String time = DateUtil.format(curDate, "yyyy年MM月");
        String cu = DateUtil.format(curDate, "yyyy-MM-dd HH:mm");
        CommonUtils.log("time1", time);
        String dataString = formatter.format(curDate);
        TimeSelector timeSelectorDialog = new TimeSelector(getActivity(), new TimeSelector.ResultHandler2() {
            @Override
            public void handle(Calendar calendar) {
                String time = DateUtil.format(calendar.getTime(), "yyyy年MM月dd");
                CommonUtils.log("time2", time);
                CommonUtils.log("day", DateUtil.format(calendar.getTime(), "dd"));
            }

            @Override
            public void handle(String time) {
                CommonUtils.log("time1", time);
            }
        }, "2010-1-1 00:00", cu).setCurrentTime(cu);
        timeSelectorDialog.setScrollUnit(TimeSelector.SCROLLTYPE.YEAR,
                TimeSelector.SCROLLTYPE.MONTH, TimeSelector.SCROLLTYPE.DAY
        )
                .setJustShowScrollUnit(true);
        timeSelectorDialog.show();
    }


    /**
     * 设置TextView段落间距
     *
     * @param tv               给谁设置段距，就传谁
     * @param content          文字内容
     * @param paragraphSpacing 请输入段落间距（单位dp）
     */
    public static void setParagraphSpacing(TextView tv, String content, int paragraphSpacing) {
        if (!content.contains("\n")) {
            tv.setText(content);
            return;
        }
        content = content.replace("\n", "\n\r");
        int previousIndex = content.indexOf("\n\r");
        //记录每个段落开始的index，第一段没有，从第二段开始
        List<Integer> nextParagraphBeginIndexes = new ArrayList<>();
        nextParagraphBeginIndexes.add(previousIndex);
        while (previousIndex != -1) {
            int nextIndex = content.indexOf("\n\r", previousIndex + 2);
            previousIndex = nextIndex;
            if (previousIndex != -1) {
                nextParagraphBeginIndexes.add(previousIndex);
            }
        }
        //把\r替换成透明长方形（宽:1px，高：字高+段距）
        SpannableString spanString = new SpannableString(content);
        ColorDrawable gd = new ColorDrawable();
        gd.setColor(Color.RED);
        gd.setAlpha(1);
        //int强转部分为：行高 - 行距 + 段距
        int height = (int) (tv.getLineHeight() - tv.getLineSpacingExtra() + paragraphSpacing);
        gd.setBounds(0, 0, 1, height);
        for (int index : nextParagraphBeginIndexes) {
            spanString.setSpan(new ImageSpan(gd), index + 1, index + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(spanString);
    }


    public void onClickLeft() {
        CommonUtils.log(binding.homeEtValidate.getText().toString().length());
        CommonUtils.log(binding.homeEtValidate.getText().toString());
        IdCardUtil idCardUtil = new IdCardUtil(binding.homeEtValidate.getText().toString());
        int correct = idCardUtil.isCorrect();
        CommonUtils.showToast(getActivity(), idCardUtil.getErrMsg());

    }


    public void onClickText01() {
        CommonUtils.showToast(getActivity(), "" + binding.textView001.isSelected());
        binding.textView001.setSelected(!binding.textView001.isSelected());
        ArrayList<ChoosePopWindow.OneTextBean> show = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            ChoosePopWindow.OneTextBean bean = new ChoosePopWindow.OneTextBean("TTT" + i, "" + i);
            show.add(bean);
        }
        new ChoosePopWindow.Builder()
                .context(getActivity())
                .showData(show)
                .callBack(new ChoosePopWindow.CallBack() {
                    @Override
                    public void onResult(Object o) {
                        CommonUtils.showToast(getActivity(), o.toString());
                    }
                })
                .build()

                .showAtLocation(binding.textView001, Gravity.NO_GRAVITY, 0, 0);

    }

    public void onClickRight() {

        Test.TestParamBuilder paramBuilder = new Test.TestParamBuilder(this);
//        paramBuilder.getBrandList("car", new StateCallBack<List<Test.Bean>>() {
//            @Override
//            public void onError(int errorCode, String msg, Object... other) {
//                CommonUtils.log("onError");
//            }
//
//            @Override
//            public void onSuccess(List<Test.Bean> data) {
//                CommonUtils.log("onSuccess",  data.size());
//            }
//
//            @Override
//            public void onLoading(String msg) {
//                CommonUtils.log();
//            }
//        });
        paramBuilder.koubeiDetail(new StateCallBack<Test.KoubeiBean>() {
            @Override
            public void onError(int errorCode, String msg, Object... other) {

            }

            @Override
            public void onSuccess(Test.KoubeiBean data) {

            }

            @Override
            public void onLoading(String msg) {

            }
        });
    }
}
