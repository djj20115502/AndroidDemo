package djjtest.com.androiddemo;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;

import djjtest.com.androiddemo.databinding.ActivityMainBinding;
import djjtest.com.androiddemo.slidelayout.SlideFragment;
import djjtest.com.androiddemo.slidelayout.transformer.GalleryTransformer;
import djjtest.com.androiddemo.test.itemDecoration.ItemDecorationFragment;
import djjtest.com.androiddemo.view.TestFragment;

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
        Fresco.initialize(MainActivity.this);
        binding = DataBindingUtil.setContentView(this, layout_id);
        initView();
    }


    private void initView() {
//        fragmentArrayList.add(new TestFragment());
        fragmentArrayList.add(new ItemDecorationFragment());
//        fragmentArrayList.add(new SlideFragment().setTitle("滑动"));
        adapter = new FragmentAdapter(getSupportFragmentManager(), fragmentArrayList);
        binding.viewpager.setPageTransformer(true, new GalleryTransformer());
        binding.viewpager.setAdapter(adapter);
        binding.viewpager.setClipChildren(false);
        binding.tablayout.setupWithViewPager(binding.viewpager);
    }
}
