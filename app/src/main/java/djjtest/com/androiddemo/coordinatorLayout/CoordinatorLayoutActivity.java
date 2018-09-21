package djjtest.com.androiddemo.coordinatorLayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import djjtest.com.androiddemo.R;

public class CoordinatorLayoutActivity extends Activity {
    @BindView(R.id.coordinatorLayout_activity_collapsingToolbarLayout)
    CollapsingToolbarLayout toolbarLayout;

    @BindView(R.id.coordinatorLayout_activity_appBarLayout)
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinatorlayout_activity);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    toolbarLayout.setContentScrimColor(0xfff000);
                } else {
                    toolbarLayout.setContentScrim(null);
                }
            }
        });
    }


}
