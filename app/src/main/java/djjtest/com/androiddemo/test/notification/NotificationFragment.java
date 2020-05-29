package djjtest.com.androiddemo.test.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.fragment.app.FragmentManager;

import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.test.BaseTestFragment;
import djjtest.com.androiddemo.utils.CommonUtils;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * author   : dongjunjie.mail@qq.com
 * time     : 2020/5/29
 * change   :
 * describe :
 */
public class NotificationFragment extends BaseTestFragment {

    public static void invoke(FragmentManager fragmentManager) {
        NotificationFragment testNest = new NotificationFragment();
        testNest.show(fragmentManager, "testNest");
    }

    @Override
    public String getTitle() {
        return "通知";
    }

    @Override
    public void initData() {
        addTest("发送通知", (v) -> {
            sendActionNotification();
        });
    }

    private void sendActionNotification() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            sendActionNotificationLess8();
        } else {
            sendActionNotification_android8();
        }
    }

    private void sendActionNotificationLess8() {
        CommonUtils.log("sendActionNotificationLess8");
//        // 创建通知(标题、内容、图标)
        Notification notification = new Notification.Builder(getActivity())
                .setContentTitle("通知标题")
                .setContentText("通知内容")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        // 创建通知管理器
        NotificationManager manager = (NotificationManager) getActivity()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // 发送通知
        manager.notify(1, notification);
    }

    private void sendActionNotification_android8() {
        CommonUtils.log("sendActionNotification_android8");
        // 1. 创建一个通知(必须设置channelId)
        CommonUtils.log(android.os.Build.VERSION.SDK_INT);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            return;
        }
        Context context = getActivity().getApplicationContext();
        String channelId = "ChannelId"; // 通知渠道
        Notification notification = null;

        notification = new Notification.Builder(context)
                .setChannelId(channelId)
                .setSmallIcon(R.drawable.girl)
                .setContentTitle("通知标题")
                .setContentText("通知内容")
                .build();
        // 2. 获取系统的通知管理器(必须设置channelId)
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(
                channelId,
                "通知的渠道名称",
                NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);
        // 3. 发送通知(Notification与NotificationManager的channelId必须对应)
        notificationManager.notify(1, notification);
    }


}
