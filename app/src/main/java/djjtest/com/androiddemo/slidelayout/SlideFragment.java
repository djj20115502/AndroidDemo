package djjtest.com.androiddemo.slidelayout;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import djjtest.com.androiddemo.FragmentAdapter;
import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.databinding.SlideLayoutBinding;
import djjtest.com.androiddemo.slidelayout.view.ItemViewHolder;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Author      :    DongJunJie
 * Date        :    2018/11/30
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class SlideFragment extends FragmentAdapter.BaseFragment {

    private static final int layout_id = R.layout.slide_layout;

    SlideLayoutBinding binding;

    @Override
    protected CharSequence getTitle() {
        return "滑动";
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(layout_id, container, false);
        binding = DataBindingUtil.bind(v);
        initView();
        v.setOnTouchListener(onTouchListener);
        v.setTag(firstcolor);
        return v;
    }

    MultiTypeAdapter adapter;
    ArrayList<Object> items = new ArrayList<>();

    private void initView() {
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        adapter = new MultiTypeAdapter(items);
        items.add(new ItemViewHolder.Data(firstcolor));
        items.add(new ItemViewHolder.Data(0x33f00ff0));
        items.add(new ItemViewHolder.Data(0x22f00fff));
        items.add(new ItemViewHolder.Data(0x11f0ffff));
        items.add(new ItemViewHolder.Data(0x00f0ffff));

        ItemViewHolder.inject(adapter);
        binding.rv.setLayoutManager(lm);
        binding.rv.setAdapter(adapter);
    }

    int firstcolor;

    public SlideFragment firstColor(int color) {
        firstcolor = color;
        return this;
    }

    View.OnTouchListener onTouchListener=new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    };

    public SlideFragment onTouchListener(View.OnTouchListener var) {
        onTouchListener = var;
        return this;
    }
}
