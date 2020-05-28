package djjtest.com.androiddemo.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.crashlytics.android.Crashlytics;

import djjtest.com.androiddemo.MainActivity;
import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.base.BaseMultiTypeViewHolder;
import djjtest.com.androiddemo.base.HeaderAndFooterAdapter;
import djjtest.com.androiddemo.coordinatorLayout.CoordinatorLayoutFragment;
import djjtest.com.androiddemo.databinding.MainTestBinding;
import djjtest.com.androiddemo.test.faf.FAFMain;
import djjtest.com.androiddemo.test.nesttest.TestNest;
import djjtest.com.androiddemo.test.nesttest.TestNest2;
import djjtest.com.androiddemo.test.popanddilog.DilogFragment;
import io.fabric.sdk.android.Fabric;
import me.drakeet.multitype.MultiTypeAdapter;

public class MainTestActivity extends AppCompatActivity {

    private void test() {
        addTest("main", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.invoke(mActivity);
            }
        });
        addTest(" CoordinatorLayout", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoordinatorLayoutFragment.invoke(getSupportFragmentManager());
            }
        });
        addTest(" FAFMain", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FAFMain.invoke(getSupportFragmentManager());
            }
        });
        addTest(" DilogFragment", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DilogFragment.invoke(getSupportFragmentManager());
            }
        });
        addTest(" TestNest", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestNest.invoke(getSupportFragmentManager());
            }
        });
        addTest(" TestNest2", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestNest2.invoke(getSupportFragmentManager());
            }
        });
        addTest(" first-error", (v) ->
                Crashlytics.getInstance().crash()

        );

    }


    MainTestBinding binding;
    HeaderAndFooterAdapter mHeaderAndFooterAdapter = new HeaderAndFooterAdapter();
    MainTestActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        binding = DataBindingUtil.setContentView(this, R.layout.main_test);
        mActivity = this;
        test();
        TestViewHolder.inject(mHeaderAndFooterAdapter);
//        binding.testRv.setLayoutManager(new HorizontallyLooperLayoutManager());
        binding.testRv.setLayoutManager(new GridLayoutManager(this, 3));
//        binding.testRv.setLayoutManager(new VerticallyLooperLayoutManager());
        binding.testRv.setAdapter(mHeaderAndFooterAdapter);
    }


    public MainTestActivity addTest(String title, View.OnClickListener onClickListener) {
        mHeaderAndFooterAdapter.getItems().add(new TestBean(title, onClickListener));
        return this;
    }

    public static class TestBean {
        String title;
        View.OnClickListener onClickListener;

        public TestBean(String title, View.OnClickListener onClickListener) {
            this.title = title;
            this.onClickListener = onClickListener;
        }
    }


    public static class TestViewHolder extends BaseMultiTypeViewHolder<TestBean> {

        public static void inject(MultiTypeAdapter adapter) {
            BaseMultiTypeViewHolder.inject(adapter, R.layout.main_test_item, TestViewHolder.class);
        }

        Button button;

        public TestViewHolder(View itemView) {
            super(itemView);
            button = (Button) itemView;
        }

        @Override
        public void bind(TestBean testBean) {
            button.setText(testBean.title);
            button.setOnClickListener(testBean.onClickListener);
        }

        @Override
        public void clearViewState() {

        }
    }
}
