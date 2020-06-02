package djjtest.com.androiddemo.test;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.base.BaseDialogFragment;
import djjtest.com.androiddemo.base.HeaderAndFooterAdapter;
import djjtest.com.androiddemo.databinding.MainTestFragmentBinding;


/**
 * author   : dongjunjie.mail@qq.com
 * time     : 2020/5/29
 * change   :
 * describe :
 */
public abstract class BaseTestFragment extends BaseDialogFragment<MainTestFragmentBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.main_test_fragment;
    }

    HeaderAndFooterAdapter adapter = new HeaderAndFooterAdapter();

    @Override
    protected void initView() {
        initData();
        MainTestActivity.TestViewHolder.inject(adapter);
        binding.testRv.setAdapter(adapter);
        binding.testRv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        binding.title.setText(getTitle());
    }

    public abstract String getTitle();

    public abstract void initData();


    public BaseTestFragment addTest(String title, View.OnClickListener onClickListener) {
        adapter.getItems().add(new MainTestActivity.TestBean(title, onClickListener));
        return this;
    }
}
