package djjtest.com.androiddemo;

import java.security.PublicKey;

/**
 * Author      :    DongJunJie
 * Date        :    2018/12/18
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class Constants {

    public static boolean IS_TEST = true;

    public static String getTestPic(int order) {
        String[] strings = new String[]{
                "http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg",
                "http://pic33.photophoto.cn/20141022/0019032438899352_b.jpg",
                "http://pic1.nipic.com/2008-12-30/200812308231244_2.jpg",
                "http://www.pptbz.com/pptpic/UploadFiles_6909/201203/2012031220134655.jpg",
                "http://pic21.nipic.com/20120519/5454342_154115399000_2.jpg",
                "http://pic22.nipic.com/20120704/10243327_181334497194_2.jpg",
                "http://pic1.nipic.com/2009-02-10/2009210213644146_2.jpg"
        };
        return strings[order % strings.length];
    }
}
