package djjtest.com.androiddemo.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.databinding.DailogBinding;

/**
 * Author      :    DongJunJie
 * Date        :    2019/1/22
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class NotifyDialog extends Dialog {
    int layout_id = R.layout.dailog;

    Builder mBuilder;


    DailogBinding binding;

    private NotifyDialog(Context context) {
        super(context);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View v = LayoutInflater.from(context).inflate(R.layout.dailog, null);
        setContentView(v);
        v.setTag("1111");
        setCanceledOnTouchOutside(true);

    }

    private NotifyDialog(Builder builder) {
        this(builder.context);
        mBuilder = builder;
        initView();
    }

    @Override
    public void show() {
        super.show();


        Window window = getWindow();
        // 把 DecorView 的默认 padding 取消，同时 DecorView 的默认大小也会取消
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        // 设置宽度
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);
        // 给 DecorView 设置背景颜色，很重要，不然导致 Dialog 内容显示不全，有一部分内容会充当 padding，上面例子有举出
        window.getDecorView().setBackgroundColor(0x00ffffff);


    }

    private void initView() {

    }


    public void onClickClose() {
        dismiss();
    }


    public static Builder Builder(Context context) {
        return new Builder(context);
    }

    public static final class Builder {
        private CharSequence title;
        private CharSequence content;
        private CharSequence button;


        public Builder setFirstClick(View.OnClickListener firstClick) {
            this.firstClick = firstClick;
            return this;
        }

        public Builder setSecondButton(CharSequence secondButton) {
            this.secondButton = secondButton;
            return this;
        }

        public Builder setSecondClick(View.OnClickListener secondClick) {
            this.secondClick = secondClick;
            return this;
        }

        private View.OnClickListener firstClick;
        private CharSequence secondButton;
        private View.OnClickListener secondClick;

        private Context context;

        private Builder(Context context) {
            this.context = context;
        }


        public Builder title(CharSequence val) {
            title = val;
            return this;
        }

        public Builder content(CharSequence val) {
            content = val;
            return this;
        }

        public Builder button(CharSequence val) {
            button = val;
            return this;
        }

        public NotifyDialog build() {
            return new NotifyDialog(this);
        }
    }
}
