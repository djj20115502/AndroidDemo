package djjtest.com.androiddemo.test.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.FragmentManager;

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
    }

    SensorManager mSensorManager;
    SensorEventListener listener;

    private void init() {
//获取系统的SensorManager
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                onSensorChanged(event);
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
    final float MIN_TIME_BETWEEN_SAMPLES_NS = 100;
    long lastShakeTimestamp = 0;
    long mLastTimestamp = 0;
    final long SHAKING_TIME_WINDOW = 9;
    final long REQUIRED_FORCE = 2;
    float lastAX, lastAY, lastAZ;

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.timestamp - mLastTimestamp < MIN_TIME_BETWEEN_SAMPLES_NS) {
            return;
        }

        float ax = sensorEvent.values[0];
        float ay = sensorEvent.values[1];
        float az = sensorEvent.values[2] - SensorManager.GRAVITY_EARTH;

        mLastTimestamp = sensorEvent.timestamp;

        if (Math.abs(ax) > REQUIRED_FORCE && ax * lastAX <= 0) {
            recordShake(sensorEvent.timestamp);
            lastAX = ax;
        } else if (Math.abs(ay) > REQUIRED_FORCE && ay * lastAY <= 0) {
            recordShake(sensorEvent.timestamp);
            lastAY = ay;
        } else if (Math.abs(az) > REQUIRED_FORCE && az * lastAZ <= 0) {
            recordShake(sensorEvent.timestamp);
            lastAZ = az;
        }
    }

    int count = 0;

    private void recordShake(long mLastTimestamp) {
        this.lastShakeTimestamp = mLastTimestamp;
        CommonUtils.log(++count);

    }

    private void reset() {

    }
}

