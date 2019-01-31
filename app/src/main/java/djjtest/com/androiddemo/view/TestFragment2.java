package djjtest.com.androiddemo.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import djjtest.com.androiddemo.FragmentAdapter;
import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.databinding.TestFragment2Binding;
import djjtest.com.androiddemo.utils.CommonUtils;

/**
 * Author      :    DongJunJie
 * Date        :    2019/1/24
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class TestFragment2 extends FragmentAdapter.BaseFragment {
    private int layout_id = R.layout.test_fragment2;
    TestFragment2Binding binding;

    @Override
    public CharSequence getTitle() {
        return "测试2";
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

        binding.touch.setOnTouchListener(new View.OnTouchListener() {
            float mPosX, mPosY, mCurPosX, mCurPosY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CommonUtils.log(event);
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        mPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurPosX = event.getX();
                        mCurPosY = event.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        if (mCurPosX - mPosX > 8) {
                            CommonUtils.showToast(getActivity(), "right");
                        }
                        if (mCurPosX - mPosX < -8) {
                            CommonUtils.showToast(getActivity(), "left");
                        }
                        break;
                }
                return false;
            }
        });
        binding.left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.showToast(getActivity(), "left");
            }
        });
    }
}
