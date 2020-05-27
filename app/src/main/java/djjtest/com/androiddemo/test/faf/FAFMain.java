package djjtest.com.androiddemo.test.faf;

import android.graphics.Color;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import djjtest.com.androiddemo.FragmentAdapter;
import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.base.BaseDialogFragment;
import djjtest.com.androiddemo.databinding.FafmainBinding;
import djjtest.com.androiddemo.view.BaseFragment;

public class FAFMain extends BaseDialogFragment<FafmainBinding> {

    public static void invoke(FragmentManager fragmentManager) {
        FAFMain fafMain = new FAFMain();
        fafMain.show(fragmentManager, "roadBookDetailFragment");
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fafmain;
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayList<FragmentAdapter.BaseFragment> list = new ArrayList<>();
        list.add(new BaseFragment().setBackground(Color.GRAY, "GRAY"));
        list.add(new BaseFragment().setBackground(Color.YELLOW, "YELLOW"));
        list.add(new BaseFragment().setBackground(Color.BLUE, "BLUE"));
        list.add(new FAFMain2().setTitle("more"));
        binding.vp.setAdapter(new FragmentAdapter(getChildFragmentManager(), list));
        binding.tab.setupWithViewPager(binding.vp);
    }

    @Override
    protected void initView() {

    }
}
