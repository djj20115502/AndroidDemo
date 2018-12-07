package djjtest.com.androiddemo;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import djjtest.com.androiddemo.databinding.ActivityMainBinding;
import djjtest.com.androiddemo.slidelayout.SlideFragment;
import djjtest.com.androiddemo.touch.CardTouchListener;
import djjtest.com.androiddemo.transformer.CardTransformer;
import djjtest.com.androiddemo.transformer.GalleryTransformer;

/**
 * Author      :    DongJunJie
 * Date        :    2018/11/30
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class MainActivity extends AppCompatActivity {

    private static final int layout_id = R.layout.activity_main;

    ActivityMainBinding binding;
    ArrayList<FragmentAdapter.BaseFragment> fragmentArrayList = new ArrayList<>();
    FragmentAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, layout_id);
        initView();
    }

    CardTouchListener cardTouchListener;

    private void initView() {
        fragmentArrayList.add(new SlideFragment().firstColor(Color.RED));
        fragmentArrayList.add(new SlideFragment().firstColor(Color.BLUE));
        fragmentArrayList.add(new SlideFragment().firstColor(Color.BLACK));
        fragmentArrayList.add(new SlideFragment().firstColor(Color.YELLOW));
        fragmentArrayList.add(new SlideFragment().firstColor(Color.GREEN));
        adapter = new FragmentAdapter(getSupportFragmentManager(), fragmentArrayList);

        cardTouchListener = new CardTouchListener();
        binding.viewpager.setAdapter(adapter);
        binding.viewpager.setClipChildren(false);
        binding.viewpager.setPageTransformer(true, new CardTransformer());
        binding.tablayout.setupWithViewPager(binding.viewpager);
        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    }
}
