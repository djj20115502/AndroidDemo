package djjtest.com.androiddemo.slidelayout.view;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import djjtest.com.androiddemo.R;
import me.drakeet.multitype.ItemViewProvider;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Author      :    DongJunJie
 * Date        :    2018/11/30
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {

    private static final int layout_id = R.layout.slide_layout_item_view;

    public static MultiTypeAdapter inject(MultiTypeAdapter adapter) {
        adapter.register(Data.class, new ItemViewProvider<Data, ItemViewHolder>() {

            @NonNull
            @Override
            protected ItemViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
                return new ItemViewHolder(inflater.inflate(layout_id, parent, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder holder, @NonNull Data data) {
                holder.bind(data);
            }
        });
        return adapter;
    }



    public ItemViewHolder(View itemView) {
        super(itemView);

//        slidelayoutitemviewbinding
    }

    public void bind(Data data) {
        itemView.setBackgroundColor(data.color);
    }

    public static class Data {
        public Data(int var) {
            color = var;
        }

        int color;
    }


}
