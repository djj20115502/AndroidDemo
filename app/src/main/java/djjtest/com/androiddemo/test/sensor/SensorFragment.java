package djjtest.com.androiddemo.test.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.FragmentManager;
import android.view.View;

import djjtest.com.androiddemo.test.BaseTestFragment;
import djjtest.com.androiddemo.utils.CommonUtils;

public class SensorFragment extends BaseTestFragment {

    public static void invoke(FragmentManager fragmentManager) {
        SensorFragment sensorFragment = new SensorFragment();
        sensorFragment.show(fragmentManager, "testNest");
    }


    @Override
    public void test() {
        addTest("打开传感器", (v) -> init());
        addTest("关闭", (v) -> unregisterListener());

        binding.webView.setVisibility(View.VISIBLE);
        binding.webView.loadData("<html>\n" +
                "\n" +
                "<head>\n" +
                "<title>我的第一个 HTML 页面</title>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<a href=\"comtgfkcwc://m.51kcwc.com/app?apppath=single_redpacket&redpack_trigger_id=5def04d6c894c0589157f356&distribute_id=5df337bec2300000dc005584\">sdffsdf</a>\n" +
                "<p>body 元素的内容会显示在浏览器中。</p>\n" +
                "<p>title 元素的内容会显示在浏览器的标题栏中。</p>\n" +
                "</body>\n" +
                "\n" +
                "</html>", "text/html", "UTF-8");
    }

    SensorManager mSensorManager;
    SensorEventListener listener;

    private void init() {
//获取系统的SensorManager
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                doOnSensorChanged(event);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
//注册传感器监听事件
        mSensorManager.registerListener(listener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);


    }

    private void unregisterListener() {
        //注销传感器监听
        mSensorManager.unregisterListener(listener);
    }

    float lastAz, lastAy, lastAx;
    final float SENSOR_VALUE = 2;
    //最小间隔时间
    final float MIN_TIME_BETWEEN_SAMPLES_NS = 500;
    long lastShakeTimestamp = 0;
    long mLastTimestamp = 0;
    final long SHAKING_TIME_WINDOW = 9;
    final long REQUIRED_FORCE = 2;
    float lastAX, lastAY, lastAZ;

    public void doOnSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.timestamp - mLastTimestamp < MIN_TIME_BETWEEN_SAMPLES_NS) {
            return;
        }
        float ax = sensorEvent.values[0];
        float ay = sensorEvent.values[1];
        float az = sensorEvent.values[2] - SensorManager.GRAVITY_EARTH;
        mLastTimestamp = sensorEvent.timestamp;
        if (Math.abs(ax) > REQUIRED_FORCE && ax * lastAX <= 0) {
            recordShake(sensorEvent.timestamp, sensorEvent);
            lastAX = ax;
        } else if (Math.abs(ay) > REQUIRED_FORCE && ay * lastAY <= 0) {
            recordShake(sensorEvent.timestamp, sensorEvent);
            lastAY = ay;
        } else if (Math.abs(az) > REQUIRED_FORCE && az * lastAZ <= 0) {
            recordShake(sensorEvent.timestamp, sensorEvent);
            lastAZ = az;
        }
    }

    int count = 0;

    private void recordShake(long mLastTimestamp, SensorEvent sensorEvent) {
        CommonUtils.log(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
        this.lastShakeTimestamp = mLastTimestamp;
        CommonUtils.log(++count);
        binding.tip.setText("" + count);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregisterListener();
    }

    private void reset() {

    }
}

