package djjtest.com.androiddemo.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.base.BaseMultiTypeViewHolder;
import djjtest.com.androiddemo.base.HeaderAndFooterAdapter;
import djjtest.com.androiddemo.databinding.ChoosePopBinding;
import djjtest.com.androiddemo.utils.CommonUtils;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Author      :    DongJunJie
 * Date        :    2019/2/21
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class ChoosePopWindow extends PopupWindow {


    Builder mBuilder;
    private int showCenter = 3;


    ChoosePopBinding binding;

    private ChoosePopWindow(Builder builder) {
        super(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        mBuilder = builder;
        initView();
    }

    LinearLayoutManager linearLayoutManager;
    Object select;

    private void initView() {
        View contentView = View.inflate(mBuilder.context, R.layout.choose_pop, null);
        setContentView(contentView);
        binding = DataBindingUtil.bind(contentView);
        binding.setHolder(this);
        linearLayoutManager = new LinearLayoutManager(mBuilder.context);
        binding.rv.setLayoutManager(linearLayoutManager);
        setRvAdapter();
        binding.rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int count = recyclerView.getChildCount();
                showCenter = count / 2;
                for (int i = 0; i < count; i++) {
                    View v = recyclerView.getChildAt(i);
                    int position = Math.abs(i - showCenter);
                    position = Math.min(position, showCenter);
                    CommonUtils.log("position", position);
                    double scale = 1 - Math.sin(Math.PI / 6 * position) * 0.5;
                    CommonUtils.log("scale", scale);
                    v.setScaleX((float) scale);
                    v.setScaleY((float) scale);
                    v.setAlpha((float) scale);
                    if (scale == 1) {
                        v.setSelected(scale == 1);
                        select = recyclerView.getChildAdapterPosition(v);
                        if (mBuilder.adapter == null && mBuilder.showData != null) {
                            select = mBuilder.showData.get((int) select).getResult();
                        }
                    }
                }
            }
        });
        new LinearSnapHelper().attachToRecyclerView(binding.rv);

        if (mBuilder.leftButtonText == null) {
            mBuilder.leftButtonText = "取消";
        }
        binding.leftButton.setText(mBuilder.leftButtonText);
        if (mBuilder.rightButtonText == null) {
            mBuilder.rightButtonText = "确认";
        }
        binding.rightButton.setText(mBuilder.rightButtonText);
    }


    private void setRvAdapter() {
        if (mBuilder.adapter != null) {
            binding.rv.setAdapter(mBuilder.adapter);
            return;
        }
        HeaderAndFooterAdapter andFooterAdapter = new HeaderAndFooterAdapter(mBuilder.showData);
        OneTextHolder.inject(andFooterAdapter);
        binding.rv.setAdapter(andFooterAdapter);
    }

    public void onClickLeft() {
        if (mBuilder.leftButtonClick != null) {
            mBuilder.leftButtonClick.onClick(binding.leftButton);
        }
        dismiss();
    }

    public void onClickRight() {
        if (mBuilder.rightButtonClick != null) {
            mBuilder.rightButtonClick.onClick(binding.rightButton);
            return;
        }
        mBuilder.callBack.onResult(select);
        dismiss();
    }

    public interface JustOneText<T> {
        String getShowText();

        T getResult();
    }


    public static class OneTextBean implements JustOneText<Object> {


        public OneTextBean(String show) {
            this.show = show;
        }

        JustOneText justOneText;

        public OneTextBean(JustOneText justOneText) {
            this.justOneText = justOneText;
        }


        String show;

        @Override
        public String getShowText() {
            if (justOneText != null) {
                return justOneText.getShowText();
            }
            return show;
        }

        @Override
        public Object getResult() {
            if (justOneText != null) {
                return justOneText.getResult();
            }
            return show;
        }
    }

    public static class OneTextHolder extends BaseMultiTypeViewHolder<OneTextBean> {


        public static void inject(MultiTypeAdapter adapter) {
            BaseMultiTypeViewHolder.inject(adapter, R.layout.on_text_view2, OneTextHolder.class);
        }


        public OneTextHolder(View itemView) {
            super(itemView);
        }


        @Override
        public void bind(final OneTextBean s) {
            ((TextView) itemView).setText(s.getShowText());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonUtils.log(s.getResult());
                }
            });
            itemView.setTag(s.getShowText());
        }


        @Override
        public void clearViewState() {

        }


    }


    public interface CallBack {

        /**
         * 如果使用通用的showData  返回的是相应的 {@link JustOneText#getResult()}
         * 如果是使用自定义adapter 返回相应的 数据位置 int；
         */
        void onResult(Object o);
    }

    public static final class Builder {
        private CharSequence leftButtonText;
        private CharSequence rightButtonText;
        private View.OnClickListener leftButtonClick;
        private View.OnClickListener rightButtonClick;
        private HeaderAndFooterAdapter adapter;
        private ArrayList<OneTextBean> showData;
        private Context context;
        private CallBack callBack;

        public Builder() {
        }

        public Builder leftButtonText(CharSequence val) {
            leftButtonText = val;
            return this;
        }

        public Builder callBack(CallBack val) {
            callBack = val;
            return this;
        }

        public Builder rightButtonText(CharSequence val) {
            rightButtonText = val;
            return this;
        }

        public Builder leftButtonClick(View.OnClickListener val) {
            leftButtonClick = val;
            return this;
        }

        public Builder rightButtonClick(View.OnClickListener val) {
            rightButtonClick = val;
            return this;
        }

        public Builder adapter(HeaderAndFooterAdapter val) {
            adapter = val;
            return this;
        }

        public Builder showData(List<? extends JustOneText> val) {
            showData = new ArrayList<>();
            for (JustOneText justOneText : val) {
                showData.add(new OneTextBean(justOneText));
            }
            return this;
        }

        public Builder context(Context val) {
            context = val;
            return this;
        }

        public ChoosePopWindow build() {
            if (adapter == null && showData == null) {
                throw new IllegalArgumentException("adapter==null||showData==null");
            }
            if (adapter != null && showData != null) {
                throw new IllegalArgumentException("adapter != null && showData != null");
            }
            return new ChoosePopWindow(this);
        }
    }
}
