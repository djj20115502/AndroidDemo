package djjtest.com.androiddemo.view;

import androidx.databinding.BindingAdapter;
import android.net.Uri;
import android.text.TextUtils;

import com.facebook.drawee.view.SimpleDraweeView;

import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.utils.CommonUtils;

/**
 * Author      :    DongJunJie
 * Date        :    2019/1/11
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class Bean {
    public Bean(String name, String url, int sex, boolean isVip) {
        this.name = name;
        this.url = url;
        this.sex = sex;
        this.vip = isVip;
    }

    public boolean vip;
    public String name;
    public String url;
    public int sex;

    @BindingAdapter({"headuri", "sex"})
    public static void setImgUrl(SimpleDraweeView imageView, String headuri, int sex) {
        CommonUtils.log(headuri, sex);
        if (!TextUtils.isEmpty(headuri)) {
            imageView.setImageURI(headuri);
            return;
        }
        if (sex == 1) {
            imageView.setImageURI((new Uri.Builder()).scheme("res").path(String.valueOf(R.drawable.boy)).build());
        } else if (sex == 0) {
            imageView.setImageURI((new Uri.Builder()).scheme("res").path(String.valueOf(R.drawable.icon_no_sex)).build());
        } else {
            imageView.setImageURI((new Uri.Builder()).scheme("res").path(String.valueOf(R.drawable.girl)).build());
        }
    }
}
