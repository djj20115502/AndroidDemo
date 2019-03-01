package djjtest.com.androiddemo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;

import djjtest.com.androiddemo.databinding.ActivityMainBinding;
import djjtest.com.androiddemo.slidelayout.SlideFragment;
import djjtest.com.androiddemo.test.itemDecoration.ItemDecorationFragment;
import djjtest.com.androiddemo.utils.CommonUtils;
import djjtest.com.androiddemo.view.TestFragment;
import djjtest.com.androiddemo.view.TestFragment2;

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
        fragmentArrayList.add(new TestFragment());
//        fragmentArrayList.add(new TestFragment2());
//        fragmentArrayList.add(new ItemDecorationFragment());
//        fragmentArrayList.add(new SlideFragment().setTitle("滑动"));
        adapter = new FragmentAdapter(getSupportFragmentManager(), fragmentArrayList);
//        binding.viewpager.setPageTransformer(true, new GalleryTransformer());
        binding.viewpager.setAdapter(adapter);
        binding.viewpager.setClipChildren(false);
        binding.tablayout.setupWithViewPager(binding.viewpager);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        CommonUtils.log(" dispatchKeyEvent",event);
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
          View v= getCurrentFocus();
          if(v!=null){
              CommonUtils.log(" getName",v.getClass().getName());
              if(v instanceof EditText){
                  CommonUtils.log("isFocusable",getWindow().getDecorView().isFocusable());
                  getWindow().getDecorView().requestFocus();
              }
          }
        }
        return super.dispatchKeyEvent(event);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == KeyEvent.ACTION_DOWN
                && getCurrentFocus() != null
                && getCurrentFocus() instanceof EditText) {
            getWindow().getDecorView().setFocusable(true);
            getWindow().getDecorView().requestFocus();
        }
        return super.dispatchTouchEvent(ev);
    }


}
