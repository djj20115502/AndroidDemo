package djjtest.com.androiddemo.flutter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import io.flutter.facade.Flutter;

public class FlutterPageActivity extends AppCompatActivity {


    public static Intent getInvokeIntent(Context context) {
        Intent intent = new Intent(context, FlutterPageActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 通过FlutterView引入Flutter编写的页面
        View flutterView = Flutter.createView(this, getLifecycle(), "route1");
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(600, 800);
        layout.leftMargin = 100;
        layout.topMargin = 200;
        addContentView(flutterView, layout);
    }
}
