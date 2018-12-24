package djjtest.com.androiddemo.test.itemDecoration;

import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import djjtest.com.androiddemo.FragmentAdapter;
import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.databinding.ItemdecorationBinding;
import djjtest.com.androiddemo.utils.CommonUtils;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Author      :    DongJunJie
 * Date        :    2018/12/19
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class ItemDecorationFragment extends FragmentAdapter.BaseFragment {

    int layout_id= R.layout.itemdecoration;


    ItemdecorationBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(layout_id, container, false);
        binding = DataBindingUtil.bind(v);
        initView();
        return v;
    }

    @Override
    public CharSequence getTitle() {
        return "间隔";
    }

    private void initView() {

        ArrayList<Object> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            RecommendGridItemHolder.Data data = new RecommendGridItemHolder.Data();
            data.name = "name" + i;
            data.price = "price" + i;
            data.url = "http://img3.imgtn.bdimg.com/it/u=1752243568,253651337&fm=26&gp=0.jpg";
            list.add(data);
        }

        MultiTypeAdapter adapter = new MultiTypeAdapter(list);
        RecommendGridItemHolder.inject(adapter);
        Resources rs = binding.recommendCarContent.getContext().getResources();
        binding.recommendCarContent.addItemDecoration(new GirdItemDecoration.Builder()
                .bottomMargin(CommonUtils.dp2px(rs, 10))
                .centerMargin(CommonUtils.dp2px(rs, 10))
                .edgeMargin(CommonUtils.dp2px(rs, 15))
                .build());
        binding.recommendCarContent.setAdapter(adapter);
    }
}
