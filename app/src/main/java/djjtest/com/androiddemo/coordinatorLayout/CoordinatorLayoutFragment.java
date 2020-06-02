package djjtest.com.androiddemo.coordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;
import androidx.fragment.app.FragmentManager;

import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.base.BaseDialogFragment;
import djjtest.com.androiddemo.databinding.CoordinatorlayoutActivityBinding;

public class CoordinatorLayoutFragment extends BaseDialogFragment<CoordinatorlayoutActivityBinding> {


    public static void invoke(FragmentManager fragmentManager) {
        CoordinatorLayoutFragment coordinatorLayoutFragment = new CoordinatorLayoutFragment();
        coordinatorLayoutFragment.show(fragmentManager, "coordinatorLayoutFragment");
    }


    @Override
    protected int getLayoutId() {
        return R.layout.coordinatorlayout_activity;
    }


    @Override
    protected void initView() {
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
//
    }


}
