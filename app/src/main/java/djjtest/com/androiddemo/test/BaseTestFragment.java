package djjtest.com.androiddemo.test;

import android.view.View;

import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.base.BaseDialogFragment;
import djjtest.com.androiddemo.base.HeaderAndFooterAdapter;
import djjtest.com.androiddemo.databinding.MainTestBinding;

public abstract class BaseTestFragment extends BaseDialogFragment<MainTestBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.main_test;
    }

    @Override
    public void onStart() {
        super.onStart();
        test();
        MainTestActivity.TestViewHolder.inject(mHeaderAndFooterAdapter);
        binding.testRv.setAdapter(mHeaderAndFooterAdapter);
    }

    @Override
    protected void initView() {

    }

    public abstract void test();


    HeaderAndFooterAdapter mHeaderAndFooterAdapter = new HeaderAndFooterAdapter();


    public BaseTestFragment addTest(String title, View.OnClickListener onClickListener) {
        mHeaderAndFooterAdapter.getItems().add(new MainTestActivity.TestBean(title, onClickListener));
        return this;
    }


}
