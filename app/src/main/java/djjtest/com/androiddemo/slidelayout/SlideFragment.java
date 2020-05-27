package djjtest.com.androiddemo.slidelayout;

import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import djjtest.com.androiddemo.FragmentAdapter;
import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.databinding.SlideLayoutBinding;
import djjtest.com.androiddemo.view.BaseFragment;

/**
 * Author      :    DongJunJie
 * Date        :    2018/11/30
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class SlideFragment extends FragmentAdapter.BaseFragment {

    private static final int layout_id = R.layout.slide_layout;

    SlideLayoutBinding binding;
    ArrayList<FragmentAdapter.BaseFragment> fragmentArrayList = new ArrayList<>();
    FragmentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(layout_id, container, false);
        binding = DataBindingUtil.bind(v);
        initView();
        return v;
    }

    private void initView() {

        adapter = new FragmentAdapter(getFragmentManager(), fragmentArrayList);
        init();
        binding.vp.setAdapter(adapter);
        binding.vp.setClipChildren(false);
        binding.ty.setupWithViewPager(binding.vp);
        binding.right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.vp.performSlipRight(true);
            }
        });
        binding.left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.vp.performSlipLeft(true);
            }
        });
        binding.vp.setCallBack(new SlideViewPager.CallBack() {
            @Override
            public void onLeft() {

            }

            @Override
            public void onRight() {
//                Log.e("djjtest", "getCurrentItem " + binding.vp.getCurrentItem() + " fragmentArrayList：" + fragmentArrayList.get(binding.vp.getCurrentItem()).getTitle());
                Log.e("djjtest", "getCurrentItem " + (binding.vp.getCurrentItem()-1) + " fragmentArrayList：" + fragmentArrayList.get(binding.vp.getCurrentItem()-1).getTitle()+" "+fragmentArrayList.size());

                fragmentArrayList.remove(binding.vp.getCurrentItem() - 1);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void hasNext(boolean var) {
//                if (!var) {
//                    init();
//                }
            }

            @Override
            public void hasUndo(boolean var) {
                binding.left.setVisibility(var ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void init() {
        fragmentArrayList.add(new BaseFragment().setBackground(Color.RED, "RED"));
        fragmentArrayList.add(new BaseFragment().setBackground(Color.BLUE, "BLUE"));
        fragmentArrayList.add(new BaseFragment().setBackground(Color.BLACK, "BLACK"));
        fragmentArrayList.add(new BaseFragment().setBackground(Color.YELLOW, "YELLOW"));
        fragmentArrayList.add(new BaseFragment().setBackground(Color.GREEN, "GREEN"));
        fragmentArrayList.add(new BaseFragment().setBackground(Color.GRAY, "GRAY"));
        fragmentArrayList.add(new BaseFragment().setBackground(Color.CYAN, "CYAN"));
        adapter.notifyDataSetChanged();
    }
}
