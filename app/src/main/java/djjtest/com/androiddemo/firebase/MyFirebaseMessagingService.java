package djjtest.com.androiddemo.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import djjtest.com.androiddemo.utils.CommonUtils;

/**
 * author   : dongjunjie.mail@qq.com
 * time     : 2020/5/28
 * change   :
 * describe :
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        CommonUtils.log(s);
    }

    /**
     * {@link RemoteMessage#getData()} 这个是自定义的数据
     * <p>
     * RemoteMessage.get
     */
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        CommonUtils.log(remoteMessage, remoteMessage.getData());
        CommonUtils.log(remoteMessage.getFrom(),///376123929615
                remoteMessage.getNotification().getBody(),//222222222222222(通知内容)
                remoteMessage.getNotification().getChannelId(),//渠道
                remoteMessage.getNotification().getTitle(),//1111111111111(通知标题)
                remoteMessage.getNotification().getTag(),//campaign_collapse_key_3647062252955960122
                remoteMessage.getNotification(),///com.google.firebase.messaging.RemoteMessage$Notification@f0c4883
                remoteMessage.getSenderId(),//376123929615
                remoteMessage.getCollapseKey(),//djjtest.com.androiddemo
                remoteMessage.getTtl(),///2419200
                remoteMessage.getSentTime());///1590722330923

    }
}
