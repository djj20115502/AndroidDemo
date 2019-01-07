package djjtest.com.androiddemo.test.itemDecoration;

import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import java.util.ArrayList;

import djjtest.com.androiddemo.FragmentAdapter;
import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.databinding.ItemdecorationBinding;
import djjtest.com.androiddemo.test.rvchange.RvItem;
import djjtest.com.androiddemo.utils.CommonUtils;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Author      :    DongJunJie
 * Date        :    2018/12/19
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class ItemDecorationFragment extends FragmentAdapter.BaseFragment {

    int layout_id = R.layout.itemdecoration;


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
//        for (int i = 0; i < 6; i++) {
//            RecommendGridItemHolder.Data data = new RecommendGridItemHolder.Data();
//            data.name = "name" + i;
//            data.price = "price" + i;
//            data.url = "http://img3.imgtn.bdimg.com/it/u=1752243568,253651337&fm=26&gp=0.jpg";
//            list.add(data);
//        }
        for (int i = 0; i < 16; i++) {
            RvItem.Data no_pleased = new RvItem.Data();
            no_pleased
                    .title("最不满意" + i)
                    .defaultDes("用车过程中，有什么不满意？吐槽一下")
                    .dynamicShowEdit(true)
                    .nestedScrollView(binding.root0)
                    .showStar(true);
            list.add(no_pleased);
        }


        MultiTypeAdapter adapter = new MultiTypeAdapter(list);
        RvItem.inject(adapter);
        RecommendGridItemHolder.inject(adapter);
        Resources rs = binding.recommendCarContent.getContext().getResources();
//        binding.recommendCarContent.addItemDecoration(new GirdItemDecoration.Builder()
//                .bottomMargin(CommonUtils.dp2px(rs, 10))
//                .centerMargin(CommonUtils.dp2px(rs, 10))
//                .edgeMargin(CommonUtils.dp2px(rs, 15))
//                .build());
        binding.recommendCarContent.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recommendCarContent.setAdapter(adapter);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.log("000 getScrollY", binding.root0.getScrollY());
                CommonUtils.log("000 getScrollX", binding.root0.getScrollX());
            }
        });
        ScrollView scrollView;

    }
}
