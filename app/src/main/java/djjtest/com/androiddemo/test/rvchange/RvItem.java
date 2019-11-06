package djjtest.com.androiddemo.test.rvchange;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.ArrayList;
import java.util.List;

import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.databinding.RvChangeBinding;
import djjtest.com.androiddemo.utils.CommonUtils;
import me.drakeet.multitype.ItemViewProvider;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Author      :    DongJunJie
 * Date        :    2018/12/26
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class RvItem extends RecyclerView.ViewHolder {

    private static final int layout_id = R.layout.rv_change;


    public static MultiTypeAdapter inject(MultiTypeAdapter adapter) {
        adapter.register(Data.class, new ItemViewProvider<Data, RvItem>() {

            @NonNull
            @Override
            protected RvItem onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
                View view = inflater.inflate(layout_id, parent, false);
                return new RvItem(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull RvItem holder, @NonNull Data s) {
                holder.bind(s);
            }
        });
        return adapter;
    }


    Data data;
    List<View> stars;
    RvChangeBinding binding;

    public RvItem(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
        clearViewState();
        stars = new ArrayList<>();
        stars.add(binding.star0);
        stars.add(binding.star1);
        stars.add(binding.star2);
        stars.add(binding.star3);
        stars.add(binding.star4);


        for (int i = 0; i < stars.size(); i++) {
            stars.get(i).setTag(i);
            stars.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = (int) v.getTag();
                    int needAdd = index == data.point - 1 ? 0 : 1;
                    if (index == 0) {
                        needAdd = 1;
                    }
                    setStars(index + needAdd);
                }
            });
        }


    }

    private void setStars(int point) {
        int len = stars.size();
        point = Math.max(point, 0);
        point = Math.min(len, point);
        for (int i = 0; i < len; i++) {
            stars.get(i).setSelected(i < point);
        }
        data.point = point;
        data.nestedScrollView.setNestedScrollingEnabled(false);
        CommonUtils.log("1111111", data.nestedScrollView.getScrollY());
        if (data.dynamicShowEdit) {
            if (data.point > 0) {
                binding.content.setVisibility(View.VISIBLE);
            } else {
                binding.content.setVisibility(View.GONE);
            }
        } else {
            binding.content.setVisibility(View.VISIBLE);
        }
        CommonUtils.log("2222222", data.nestedScrollView.getScrollY());

    }

    private void startChange() {
        ViewParent vp = itemView.getParent();
        if (vp instanceof RecyclerView) {
            ((RecyclerView) vp).setNestedScrollingEnabled(false);
        }
    }

    private void endChange() {
    }


    public void clearViewState() {
        for (View v : stars) {
            v.setSelected(false);
            v.setVisibility(View.GONE);
        }
        binding.title.setText("");
        binding.content.setHint("");
        binding.content.setText(null);
    }

    public void bind(Data var) {
        this.data = var;
        clearViewState();
        if (data.showStar) {
            for (View v : stars) {
                v.setSelected(false);
                v.setVisibility(View.VISIBLE);
            }
        }
        setStars(data.point);
        binding.title.setText(data.title);
        binding.content.setHint(data.defaultDes);
        binding.content.setText(data.des);
        binding.content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                data.des = s.toString();
            }
        });
        if (data.textWatcher != null) {

            binding.content.addTextChangedListener(data.textWatcher);
        }
//        itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                if (data.title.contains("2")) {
//                    setVisibility(false);
//                }
//                itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });


    }

    public void setVisibility(boolean isVisible) {
        CommonUtils.log(itemView.getParent() == null ? "null" : itemView.getParent().getClass().getName());
        if (!(itemView.getParent() instanceof RecyclerView)) {
            itemView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
            return;
        }
        ViewGroup.LayoutParams param = itemView.getLayoutParams();
        if (isVisible) {
            param.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            param.width = ViewGroup.LayoutParams.MATCH_PARENT;
            itemView.setVisibility(View.VISIBLE);
        } else {
            itemView.setVisibility(View.GONE);
            param.height = 0;
            param.width = 0;
        }
        itemView.setLayoutParams(param);
        itemView.getParent().requestLayout();

    }

    public static final class Data {
        public int point;
        public String des;
        public String title;
        public String defaultDes;
        public boolean showStar = true;
        public boolean dynamicShowEdit = false;
        NestedScrollView nestedScrollView;
        public TextWatcher textWatcher;

        public Data() {
        }

        public Data textWatcher(TextWatcher val) {
            textWatcher = val;
            return this;
        }

        public Data point(int val) {
            point = val;
            return this;
        }

        public Data showStar(boolean val) {
            showStar = val;
            return this;
        }

        public Data defaultDes(String val) {
            defaultDes = val;
            return this;
        }

        public Data des(String val) {
            des = val;
            return this;
        }

        public Data title(String val) {
            title = val;
            return this;
        }

        /**
         * 是否点击了星星才出现text
         */
        public Data dynamicShowEdit(boolean val) {
            dynamicShowEdit = val;
            return this;
        }

        /**
         * 是否点击了星星才出现text
         */
        public Data nestedScrollView(NestedScrollView val) {
            nestedScrollView = val;
            return this;
        }
    }


}

