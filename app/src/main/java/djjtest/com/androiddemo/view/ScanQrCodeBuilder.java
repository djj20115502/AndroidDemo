package djjtest.com.androiddemo.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.utils.CommonUtils;
import me.dm7.barcodescanner.core.ViewFinderView;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Author      :    DongJunJie
 * Date        :    2019/1/23
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class ScanQrCodeBuilder extends JiFenPopWindow.BaseBuilder {
    @Override
    public int getLayoutId() {
        return R.layout.scan_qr;
    }


    @Override
    public Runnable getAnimation() {
        return null;
    }


    ZBarScannerView mScannerView;

    @Override
    public void bindData() {


        initScannerView();
    }

    private CharSequence title;

    public ScanQrCodeBuilder setTitle(CharSequence title) {
        this.title = title;
        return this;
    }


    private void initScannerView() {
//        ZBarScannerView.

        mScannerView = new ZBarScannerView(activity)
//        {
//            @Override
//            protected IViewFinder createViewFinderView(Context context) {
//                return new CustomViewFinderView(context);
//            }
//        }
        ;
        ((FrameLayout) (itemView.findViewById(R.id.content_frame))).addView(mScannerView);


        mScannerView.setResultHandler(new ZBarScannerView.ResultHandler() {
            @Override
            public void handleResult(Result result) {
                qrResult(result);
            }
        });

        mScannerView.startCamera();


        activity.getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {

                if (activity != ScanQrCodeBuilder.this.activity) {
                    return;
                }
                mScannerView.startCamera();
                CommonUtils.log("startCamera");
            }

            @Override
            public void onActivityPaused(Activity activity) {

                if (activity != ScanQrCodeBuilder.this.activity) {
                    return;
                }
                CommonUtils.log("stopCamera");
                mScannerView.stopCamera();
            }

            @Override
            public void onActivityStopped(Activity activity) {
                if (activity != ScanQrCodeBuilder.this.activity) {
                    return;
                }
                CommonUtils.log("stopCamera");
                mScannerView.stopCamera();
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (activity != ScanQrCodeBuilder.this.activity) {
                    return;
                }
                CommonUtils.log("stopCamera");
                mScannerView.stopCamera();
            }
        });
        mPopWindow.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mScannerView.stopCamera();
            }
        });
    }

    private void qrResult(Result result) {
        CommonUtils.log("qrResult");

    }

    public void onClickBack() {
        mPopWindow.dismiss();
    }


    private static class CustomViewFinderView extends ViewFinderView {
        public static final String TRADE_MARK_TEXT = "";
        public static final int TRADE_MARK_TEXT_SIZE_SP = 40;
        public final Paint PAINT = new Paint();

        public CustomViewFinderView(Context context) {
            super(context);
            init();
        }

        public CustomViewFinderView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            PAINT.setColor(Color.WHITE);
            PAINT.setAntiAlias(true);
            float textPixelSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                    TRADE_MARK_TEXT_SIZE_SP, getResources().getDisplayMetrics());
            PAINT.setTextSize(textPixelSize);
            setSquareViewFinder(true);
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawTradeMark(canvas);
        }

        private void drawTradeMark(Canvas canvas) {
            Rect framingRect = getFramingRect();
            float tradeMarkTop;
            float tradeMarkLeft;
            if (framingRect != null) {
                tradeMarkTop = framingRect.bottom + PAINT.getTextSize() + 10;
                tradeMarkLeft = framingRect.left;
            } else {
                tradeMarkTop = 10;
                tradeMarkLeft = canvas.getHeight() - PAINT.getTextSize() - 10;
            }
            canvas.drawText(TRADE_MARK_TEXT, tradeMarkLeft, tradeMarkTop, PAINT);
        }
    }

}
