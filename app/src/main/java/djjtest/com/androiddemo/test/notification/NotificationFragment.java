package djjtest.com.androiddemo.test.notification;

import androidx.fragment.app.FragmentManager;

import djjtest.com.androiddemo.test.BaseTestFragment;
import djjtest.com.androiddemo.utils.NotificationUtils;

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
            NotificationUtils.showNotification(getActivity(), "测试1", "测试内容", null);
        });
    }


}
