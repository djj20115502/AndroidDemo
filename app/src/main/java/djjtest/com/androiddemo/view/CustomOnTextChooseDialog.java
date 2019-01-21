package djjtest.com.androiddemo.view;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyyoona7.popup.EasyPopup;

import java.util.ArrayList;
import java.util.List;

import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.base.BaseMultiTypeViewHolder;
import djjtest.com.androiddemo.databinding.OnTextViewBinding;
import djjtest.com.androiddemo.utils.CommonUtils;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Author      :    DongJunJie
 * Date        :    2019/1/19
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class CustomOnTextChooseDialog {

    Builder builder;

    private CustomOnTextChooseDialog(Builder builder) {
        this.builder = builder;
        init();
    }

    public interface CallBack {
        void OnClick(Object data);
    }

    RecyclerView rv;
    boolean first = true;

    private void init() {

        LinearLayout linearLayout = new LinearLayout(builder.activity);
        rv = new RecyclerView(builder.activity);
        rv.setLayoutManager(new LinearLayoutManager(builder.activity));
        rv.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        linearLayout.addView(rv);
        rv.getLayoutParams().width = builder.width;
        rv.getLayoutParams().height = builder.height;
        builder.mCirclePop = EasyPopup.create()
                .setContentView(linearLayout)
                .setFocusAndOutsideEnable(true)
                .apply();


        if (builder.adapter == null) {
            ArrayList showList = new ArrayList();
            for (Object o : builder.list) {
                CommonUtils.log("o", o);
                OneTextHolder.Bean bean = new OneTextHolder.Bean(o, builder.callBack, builder.mCirclePop);
                showList.add(bean);
            }
            MultiTypeAdapter adapter = new MultiTypeAdapter(showList);

            OneTextHolder.inject(adapter);
            rv.setAdapter(adapter);
        } else {
            rv.setAdapter(builder.adapter);
        }

        CommonUtils.log("init");


    }


    public void showAtAnchorView(@NonNull View anchor, int vertGravity, int horizGravity, int x, int y) {


        builder.mCirclePop.showAtAnchorView(anchor, vertGravity, horizGravity, x, y);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                rv.requestLayout();
                rv.getAdapter().notifyDataSetChanged();
                CommonUtils.log("addOnScrollListener");
            }
        });

        rv.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (!first) {
                            return;
                        }
                        first = false;
                        rv.getAdapter().notifyDataSetChanged();
                        if (builder.maxHeight > 0 && rv.getHeight() > builder.maxHeight) {
                            rv.getLayoutParams().height = builder.maxHeight;
                        }
                        rv.requestLayout();
                    }
                }
        );
        rv.getAdapter().notifyDataSetChanged();
        CommonUtils.log("notifyDataSetChanged");
    }


    public static class OneTextHolder extends BaseMultiTypeViewHolder<OneTextHolder.Bean> {


        public static void inject(MultiTypeAdapter adapter) {
            BaseMultiTypeViewHolder.inject(adapter, R.layout.on_text_view, OneTextHolder.class);
        }

        OnTextViewBinding binding;

        public OneTextHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        Bean bean;

        @Override
        public void bind(Bean s) {
            itemView.requestLayout();

            CommonUtils.log("bind", s.data.toString());
            bean = s;
            ((TextView) itemView).setText(s.data.toString());
            binding.setHolder(this);

        }

        public void onClick() {
            if (bean == null) {
                return;
            }
            bean.mCirclePop.dismiss();
            bean.callBack.OnClick(bean.data);

        }

        @Override
        public void clearViewState() {

        }

        public static class Bean {

            public Object data;
            public CallBack callBack;
            public EasyPopup mCirclePop;

            public Bean(Object data, CallBack callBack, EasyPopup mCirclePop) {
                this.data = data;
                this.callBack = callBack;
                this.mCirclePop = mCirclePop;
            }
        }
    }

    public static final class Builder {
        private List list;
        private Activity activity;
        private EasyPopup mCirclePop;
        private CallBack callBack;
        private int width;
        private int height;
        private int maxHeight = -1;
        private Drawable bg;
        private RecyclerView.Adapter adapter;

        public Builder() {
        }

        public Builder list(List val) {
            list = val;
            return this;
        }

        public Builder height(int val) {
            height = val;
            return this;
        }

        public Builder activity(Activity val) {
            activity = val;
            return this;
        }

        public Builder mCirclePop(EasyPopup val) {
            mCirclePop = val;
            return this;
        }

        public Builder callBack(CallBack val) {
            callBack = val;
            return this;
        }

        public Builder width(int val) {
            width = val;
            return this;
        }

        public Builder maxHeight(int val) {
            maxHeight = val;
            return this;
        }

        public CustomOnTextChooseDialog build() {
            return new CustomOnTextChooseDialog(this);
        }
    }
}
