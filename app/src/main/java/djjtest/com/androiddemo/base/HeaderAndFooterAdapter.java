package djjtest.com.androiddemo.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import djjtest.com.androiddemo.utils.CommonUtils;
import me.drakeet.multitype.ItemViewProvider;
import me.drakeet.multitype.MultiTypeAdapter;
import me.drakeet.multitype.TypePool;

/**
 * Author      :    DongJunJie
 * Date        :    2019/1/29
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class HeaderAndFooterAdapter extends MultiTypeAdapter {

    final public ArrayList head = new ArrayList();
    final public ArrayList footer = new ArrayList();

    public HeaderAndFooterAdapter(@NonNull List<?> items) {
        super(items);
    }

    public HeaderAndFooterAdapter(@NonNull List<?> items, TypePool pool) {
        super(items, pool);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Object item = getItem(position);

        ItemViewProvider provider = getProviderByClass(onFlattenClass(item));
        try {
            Field field = ItemViewProvider.class.getDeclaredField("position");
            field.setAccessible(true);
            field.set(provider, viewHolder.getAdapterPosition());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        invoke(provider, "onBindViewHolder", new Class[]{RecyclerView.ViewHolder.class, Object.class}, new Object[]{viewHolder, onFlattenItem(item)});

    }

    private Object getItem(int position) {
        CommonUtils.log("position0", position);
        Object item;
        if (position < head.size()) {
            item = head.get(position);
            return item;
        }

        position = position - head.size();
        CommonUtils.log("position1", position);
        if (position < items.size()) {
            item = items.get(position);
            return item;
        }
        CommonUtils.log("position2", position);
        position = position - items.size();
        return footer.get(position);

    }

    @Override
    public int getItemCount() {
        CommonUtils.log("getItemCount", head.size() + items.size() + footer.size());
        return head.size() + items.size() + footer.size();
    }


    @SuppressWarnings("unchecked")
    @Override
    public int getItemViewType(int position) {
        Object item = getItem(position);
        return indexOf(onFlattenClass(item));
    }

    /**
     * 利用递归找一个类的指定方法，如果找不到，去父亲里面找直到最上层Object对象为止。
     *
     * @param clazz      目标类
     * @param methodName 方法名
     * @param classes    方法参数类型数组
     * @return 方法对象
     * @throws Exception
     */
    public static Method getMethod(Class clazz, String methodName,
                                   final Class[] classes) throws Exception {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName, classes);
        } catch (NoSuchMethodException e) {
            try {
                method = clazz.getMethod(methodName, classes);
            } catch (NoSuchMethodException ex) {
                if (clazz.getSuperclass() == null) {
                    return method;
                } else {
                    method = getMethod(clazz.getSuperclass(), methodName,
                            classes);
                }
            }
        }
        return method;
    }

    /**
     * @param obj        调整方法的对象
     * @param methodName 方法名
     * @param classes    参数类型数组
     * @param objects    参数数组
     * @return 方法的返回值
     */
    public static Object invoke(final Object obj, final String methodName,
                                final Class[] classes, final Object[] objects) {
        try {
            Method method = getMethod(obj.getClass(), methodName, classes);
            method.setAccessible(true);// 调用private方法的关键一句话
            return method.invoke(obj, objects);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object invoke(final Object obj, final String methodName,
                                final Class[] classes) {
        return invoke(obj, methodName, classes, new Object[]{});
    }

    public static Object invoke(final Object obj, final String methodName) {
        return invoke(obj, methodName, new Class[]{}, new Object[]{});
    }
}
