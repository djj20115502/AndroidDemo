package djjtest.com.androiddemo.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import djjtest.com.androiddemo.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * author   : dongjunjie.mail@qq.com
 * time     : 2020/5/29
 * change   :
 * describe :
 */
public class NotificationUtils {
    public static void showNotification(Context context, String title, String content, String imageUrl) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            sendActionNotificationLess8(context, title, content, imageUrl);
        } else {
            sendActionNotification_android8(context, title, content, imageUrl);
        }
    }

    private static void sendActionNotificationLess8(Context context,
                                                    String title,
                                                    String content,
                                                    String imageUrl) {
        CommonUtils.log("sendActionNotificationLess8");
        //        // 创建通知(标题、内容、图标)
        Notification notification = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.girl)
                .build();
        // 创建通知管理器
        NotificationManager manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // 发送通知
        manager.notify(1, notification);
    }

    private static void sendActionNotification_android8(Context context,
                                                        String title,
                                                        String content,
                                                        String imageUrl) {
        CommonUtils.log("sendActionNotification_android8");
        // 1. 创建一个通知(必须设置channelId)
        CommonUtils.log(android.os.Build.VERSION.SDK_INT);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            return;
        }
        Context context1 = context.getApplicationContext();
        String channelId = "ChannelId"; // 通知渠道
        Notification notification = null;

        notification = new Notification.Builder(context1)
                .setChannelId(channelId)
                .setSmallIcon(R.drawable.girl)
                .setContentTitle(title)
                .setContentText(content)
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
