package djjtest.com.androiddemo.model;

import androidx.annotation.CallSuper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 * Author      :    DongJunJie
 * Date        :    2018/12/14
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class ParamBuilder {


    @CallSuper
    public HashMap<String, String> buildParamsMap() {
        HashMap<String, String> map = new HashMap<>();
        Field[] allFields = this.getClass().getDeclaredFields();
        for (Field field : allFields) {
            field.setAccessible(true);
            if (Modifier.TRANSIENT == field.getModifiers()) {
                continue;
            }
            String key = field.getName();
            Object value = null;
            try {
                value = field.get(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value != null) {
                map.put(key, value.toString());
            }
        }
        return map;
    }


}
