package djjtest.com.androiddemo.test.faf;

import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import djjtest.com.androiddemo.FragmentAdapter;
import djjtest.com.androiddemo.FragmentStateAdapter;
import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.databinding.FafmainBinding;
import djjtest.com.androiddemo.view.BaseFragment;

public class FAFMain2 extends FragmentAdapter.BaseFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fafmain, container, false);

    }

    FafmainBinding binding;

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    ArrayList<FragmentAdapter.BaseFragment> list;

    protected void initView() {
        binding = DataBindingUtil.bind(getView());
        if (list == null) {
            list = new ArrayList<>();
            list.add(new BaseFragment().setBackground(Color.GRAY, "GRAY2222"));
            list.add(new BaseFragment().setBackground(Color.YELLOW, "YELLOW2222"));
            list.add(new BaseFragment().setBackground(Color.BLUE, "BLUE2222"));
        }
        binding.vp.setAdapter(new FragmentStateAdapter(getChildFragmentManager(), list));
        binding.tab.setupWithViewPager(binding.vp);
    }

}
