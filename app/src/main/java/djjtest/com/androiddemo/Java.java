package djjtest.com.androiddemo;

import java.util.HashMap;

import djjtest.com.androiddemo.model.TestBuilder;

/**
 * Author      :    DongJunJie
 * Date        :    2018/12/14
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class Java {

    public static void main(String[] arg) {
        TestBuilder t2 = new TestBuilder();
        HashMap<String, String> map = t2.buildParamsMap();
        System.out.print(map);
    }
}
