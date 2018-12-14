package djjtest.com.androiddemo.slidelayout;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import djjtest.com.androiddemo.FragmentAdapter;
import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.databinding.SlideLayoutBinding;
import djjtest.com.androiddemo.slidelayout.transformer.CardTransformer;
import djjtest.com.androiddemo.view.BaseFragment;

/**
 * Author      :    DongJunJie
 * Date        :    2018/11/30
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class SlideFragment extends FragmentAdapter.BaseFragment {

    private static final int layout_id = R.layout.slide_layout;

    SlideLayoutBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(layout_id, container, false);
        binding = DataBindingUtil.bind(v);
        initView();
        return v;
    }

    ArrayList<FragmentAdapter.BaseFragment> fragmentArrayList = new ArrayList<>();
    FragmentAdapter adapter;

    CardTouchListener cardTouchListener;

    boolean isSlideLeft = true;
    boolean isBack = false;

    private void initView() {
        fragmentArrayList.add(new BaseFragment().setBackground(Color.RED, "RED"));
        fragmentArrayList.add(new BaseFragment().setBackground(Color.BLUE, "BLUE"));
        fragmentArrayList.add(new BaseFragment().setBackground(Color.BLACK, "BLACK"));
        fragmentArrayList.add(new BaseFragment().setBackground(Color.YELLOW, "YELLOW"));
        fragmentArrayList.add(new BaseFragment().setBackground(Color.GREEN, "GREEN"));
        fragmentArrayList.add(new BaseFragment().setBackground(Color.GRAY, "GRAY"));
        fragmentArrayList.add(new BaseFragment().setBackground(Color.CYAN, "CYAN"));
        adapter = new FragmentAdapter(getFragmentManager(), fragmentArrayList);

        cardTouchListener = new CardTouchListener(new CardTouchListener.CallBack() {
            @Override
            public void slipRight() {
                performSlipRight();
            }

            @Override
            public void slipLeft() {
                performSlipLeft();
            }
        });
        binding.vp.setAdapter(adapter);
        binding.vp.setClipChildren(false);

        binding.vp.setPageTransformer(true, new CardTransformer().setCallBack(new CardTransformer.CallBack() {
            @Override
            public boolean isSlideLeft() {
                return isSlideLeft;
            }

            @Override
            public boolean isBacK() {
                return isBack;
            }
        }));
        binding.ty.setupWithViewPager(binding.vp);
        binding.vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                adapter.getCurrentPrimaryItem().getView().setOnTouchListener(cardTouchListener);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSlipRight();
            }
        });
        binding.left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSlipLeft();
            }
        });
    }


    private void performSlipLeft() {
        isSlideLeft = true;
        Log.e("djjtest", "slipLeft_" + binding.vp.getCurrentItem());
        binding.vp.setCurrentItem(binding.vp.getCurrentItem() + 1);
    }

    private void performSlipRight() {
        Log.e("djjtest", "slipRight_" + binding.vp.getCurrentItem());
        isSlideLeft = false;
        binding.vp.setCurrentItem(binding.vp.getCurrentItem() + 1);
    }
}
