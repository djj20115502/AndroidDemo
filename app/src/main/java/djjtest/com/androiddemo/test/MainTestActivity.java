package djjtest.com.androiddemo.test;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import djjtest.com.androiddemo.MainActivity;
import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.base.BaseMultiTypeViewHolder;
import djjtest.com.androiddemo.base.HeaderAndFooterAdapter;
import djjtest.com.androiddemo.coordinatorLayout.CoordinatorLayoutFragment;
import djjtest.com.androiddemo.databinding.MainTestBinding;
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
    }


    MainTestBinding binding;
    HeaderAndFooterAdapter mHeaderAndFooterAdapter = new HeaderAndFooterAdapter();
    MainTestActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_test);
        mActivity = this;
        test();
        TestViewHolder.inject(mHeaderAndFooterAdapter);
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
