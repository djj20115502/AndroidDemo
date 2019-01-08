package djjtest.com.androiddemo.test.itemDecoration;

import android.databinding.DataBindingUtil;
import android.view.View;

import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.base.BaseMultiTypeViewHolder;
import djjtest.com.androiddemo.databinding.ItemdecorationItemBinding;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Author      :    DongJunJie
 * Date        :    2018/12/19
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class RecommendGridItemHolder extends BaseMultiTypeViewHolder<RecommendGridItemHolder.Data> {
    private static int layout_id = R.layout.itemdecoration_item;

    ItemdecorationItemBinding binding;

    public static void inject(MultiTypeAdapter adapter) {
        BaseMultiTypeViewHolder.inject(adapter, layout_id , RecommendGridItemHolder.class);
    }

    public RecommendGridItemHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    @Override
    public void bind(Data data) {
        binding.price.setText(data.price);
        binding.name.setText(data.name);
        binding.icon.setImageURI(data.url);
        binding.icon.invalidate();
    }


    @Override
    public void clearViewState() {

    }


    public static class Data {

        public String price;
        public String name;
        public String url;
    }
}

