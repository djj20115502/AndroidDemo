package djjtest.com.androiddemo.view;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import djjtest.com.androiddemo.FragmentAdapter;
import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.databinding.BaseFragmentBinding;
import djjtest.com.androiddemo.utils.CommonUtils;

/**
 * Author      :    DongJunJie
 * Date        :    2018/12/14
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class BaseFragment extends FragmentAdapter.BaseFragment {

    private static final int layout_id = R.layout.base_fragment;

    BaseFragmentBinding binding;

    int color;
    String colorTile;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CommonUtils.log("BaseFragment onCreateView", title, hashCode());
        View v = inflater.inflate(layout_id, container, false);
        binding = DataBindingUtil.bind(v);
        binding.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.log("BaseFragment", title, BaseFragment.this.hashCode());
            }
        });
        v.setTag(colorTile);
        v.setBackgroundColor(color);
        return v;
    }

    public BaseFragment setBackground(int color, String title) {
        this.color = color;
        this.colorTile = title;
        this.title = title;
        return this;
    }

    @Override
    public void onResume() {
        CommonUtils.log("BaseFragment onResume", title, BaseFragment.this.hashCode());
        super.onResume();
    }
}
