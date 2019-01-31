package djjtest.com.androiddemo.view;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;

/**
 * Author      :    DongJunJie
 * Date        :    2019/1/23
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class JiFenPopWindow extends FullScreenPopWindow {

    BaseBuilder builder;

    private JiFenPopWindow(BaseBuilder builder) {
        this.builder = builder;
    }

    @Override
    protected View getContentView() {

        builder.itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                builder.bindData();
                builder.itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        builder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null) {
                    return;
                }
                if (!popupWindow.isShowing()) {
                    return;
                }
                popupWindow.dismiss();
            }
        });
        return builder.itemView;
    }

    @Override
    public void show() {
        super.show();
        if (builder.dismissTime > 0) {
            builder.itemView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            }, builder.dismissTime);
        }
        popupWindow.setOnDismissListener(builder.callBack);
        builder.activity.getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (activity == builder.activity) {
                    dismiss();
                    activity.getApplication().unregisterActivityLifecycleCallbacks(this);
                }
            }
        });
    }

    public void dismiss() {
        popupWindow.dismiss();
    }


    @Override
    protected View getTokenView() {
        return builder.activity.getWindow().getDecorView();
    }

    @Override
    protected Activity getActivity() {
        return builder.activity;
    }


    public static abstract class BaseBuilder {

        public Activity activity;
        public PopupWindow.OnDismissListener callBack;
        public View itemView;
        public JiFenPopWindow mPopWindow;

        public int dismissTime = -1;

        public abstract int getLayoutId();

        public abstract Runnable getAnimation();

        public abstract void bindData();

        public BaseBuilder dismissListener(PopupWindow.OnDismissListener callBack) {
            this.callBack = callBack;
            return this;
        }

        public BaseBuilder dismissTime(int var) {
            this.dismissTime = var;
            return this;
        }

        public JiFenPopWindow makeWindow(Activity activity) {
            this.activity = activity;
            itemView = LayoutInflater.from(activity).inflate(getLayoutId(), null);
            mPopWindow = new JiFenPopWindow(this);
            return mPopWindow;
        }
    }


}

