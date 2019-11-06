package djjtest.com.androiddemo.coordinatorLayout;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;

import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.databinding.CoordinatorlayoutActivityBinding;

public class CoordinatorLayoutActivity extends Activity {

    CoordinatorlayoutActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ViewDataBinding binding = DataBindingUtil.setContentView(this, R.layout.coordinatorlayout_activity);
//        this.binding=(CoordinatorlayoutActivityBinding)binding;
        initView();
    }

    private void initView() {
        binding.coordinatorLayoutActivityAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    binding.coordinatorLayoutActivityCollapsingToolbarLayout.setContentScrimColor(0xfff000);
                } else {
                    binding.coordinatorLayoutActivityCollapsingToolbarLayout.setContentScrim(null);
                }
            }
        });
    }


}
