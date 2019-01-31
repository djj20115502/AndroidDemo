package djjtest.com.androiddemo.test;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.utils.CommonUtils;
import djjtest.com.androiddemo.view.ScanQrCodeBuilder;
import me.dm7.barcodescanner.core.ViewFinderView;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Author      :    DongJunJie
 * Date        :    2019/1/23
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class TA extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_qr);
    }




    ZBarScannerView mScannerView;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScannerView.stopCamera();
    }

    private CharSequence title;


    @Override
    protected void onStart() {
        super.onStart();
        initScannerView();
    }

    private void initScannerView() {
//        ZBarScannerView.
        mScannerView = new ZBarScannerView(this)
//        {
//            @Override
//            protected IViewFinder createViewFinderView(Context context) {
//                return new CustomViewFinderView(context);
//            }
//        }
        ;
        ((FrameLayout) (findViewById(R.id.content_frame))).addView(mScannerView);

        CommonUtils.log("flagflagflagflagflagflagflag");

        mScannerView.setResultHandler(new ZBarScannerView.ResultHandler() {
            @Override
            public void handleResult(Result result) {
                qrResult(result);
            }
        });
        CommonUtils.log("startCamera");
        mScannerView.startCamera();


    }

    private void qrResult(Result result) {
        CommonUtils.log("qrResult",result.getContents());

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
