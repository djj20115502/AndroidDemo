package djjtest.com.androiddemo.coordinatorLayout;

import android.app.Activity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.AppBarLayout;

import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.databinding.CoordinatorlayoutActivityBinding;

public class CoordinatorLayoutActivity extends Activity {

    CoordinatorlayoutActivityBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinatorlayout_activity);
        binding = DataBindingUtil.setContentView(this, R.layout.coordinatorlayout_activity);

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
