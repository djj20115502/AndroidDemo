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
import djjtest.com.androiddemo.flutter.FlutterPageActivity;
import djjtest.com.androiddemo.readexcel.ReadExcel;
import djjtest.com.androiddemo.test.faf.FAFMain;
import djjtest.com.androiddemo.test.nesttest.TestNest;
import djjtest.com.androiddemo.test.nesttest.TestNest2;
import djjtest.com.androiddemo.test.popanddilog.DilogFragment;
import djjtest.com.androiddemo.test.sensor.SensorFragment;
import djjtest.com.androiddemo.utils.CommonUtils;
import me.drakeet.multitype.MultiTypeAdapter;

public class MainTestActivity extends AppCompatActivity {

    private void test() {
        addTest("main", (v) -> MainActivity.invoke(mActivity));
        addTest(" CoordinatorLayout",
                (v) -> CoordinatorLayoutFragment.invoke(getSupportFragmentManager()));
        addTest(" FAFMain", v -> FAFMain.invoke(getSupportFragmentManager()));
        addTest(" DilogFragment", v -> DilogFragment.invoke(getSupportFragmentManager()));
        addTest(" TestNest", v -> TestNest.invoke(getSupportFragmentManager()));
        addTest(" TestNest2", v -> TestNest2.invoke(getSupportFragmentManager()));
        addTest(" ReadExcel", v -> new Thread(() -> {
            CommonUtils.log(getFilesDir() + "/right.x1s");
            ReadExcel.read(getFilesDir() + "/right.x1s");
        }).run());
        addTest(" flutter", v -> this.startActivity(FlutterPageActivity.getInvokeIntent(this))
        );
        addTest(" 摇一摇传感器", (View v) -> SensorFragment.invoke(getSupportFragmentManager()));

//        for (int i = 0; i < 30; i++) {
//            addTest("" + i + CommonUtils.getTest() + "E", (View v) -> CommonUtils.showToast(this, "" + binding.testRv.getChildCount()));
//        }

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
//        binding.testRv.setLayoutManager(new HorizontallyLooperLayoutManager());
//        binding.testRv.setLayoutManager(new VerticallyLooperLayoutManager());
        binding.testRv.setLayoutManager(new TestLM(this));
        binding.testRv.setAdapter(mHeaderAndFooterAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();

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
