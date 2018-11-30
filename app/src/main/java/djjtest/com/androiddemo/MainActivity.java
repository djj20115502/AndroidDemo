package djjtest.com.androiddemo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import djjtest.com.androiddemo.databinding.ActivityMainBinding;
import djjtest.com.androiddemo.slidelayout.SlideFragment;

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

    private void initView() {
        fragmentArrayList.add(new SlideFragment());
//        fragmentArrayList.add(new SlideFragment());
        adapter = new FragmentAdapter(getSupportFragmentManager(), fragmentArrayList);
        binding.viewpager.setAdapter(adapter);
        binding.tablayout.setupWithViewPager(binding.viewpager);

    }
}
