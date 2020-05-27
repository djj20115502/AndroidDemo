package djjtest.com.androiddemo.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
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

    final public ArrayList<Object> head = new ArrayList<>();
    final public ArrayList<Object> footer = new ArrayList<>();
    final HashMap<Class, ClickCallBack> clickMap = new HashMap<>();

    /**
     * 设置item点击事件，在bind之后设置 参数为当前绑定的数据
     */
    public <T> HeaderAndFooterAdapter setClickCallBack(Class<T> t, ClickCallBack<T> clickCallBack) {
        clickMap.put(t, clickCallBack);
        return this;
    }

    private boolean flag = false;

    public HeaderAndFooterAdapter() {
        super(new ArrayList<>());
        flag = true;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Object> getItems() {
        if (flag) {
            return (ArrayList<Object>) items;
        } else {
            throw new IllegalArgumentException("必须用 HeaderAndFooterAdapter()构造方法的才能用这个方法");
        }
    }

    public interface ClickCallBack<T> {
        void onClick(T bean);
    }

    public HeaderAndFooterAdapter(@NonNull List<?> items) {
        super(items);
    }

    public HeaderAndFooterAdapter(@NonNull List<?> items, TypePool pool) {
        super(items, pool);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
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
        Object data = onFlattenItem(item);
        invoke(provider, "onBindViewHolder", new Class[]{RecyclerView.ViewHolder.class, Object.class}, new Object[]{viewHolder, data});
        ClickCallBack clickCallBack = clickMap.get(data.getClass());
        CommonUtils.log("HeaderAndFooterAdapter", "clickCallBack", clickCallBack == null ? "null" : clickCallBack.getClass().getName());
        if (clickCallBack != null) {
            try {
                OnClickListener listener = new OnClickListener(clickCallBack, getItem(position));
                viewHolder.itemView.setOnClickListener(listener);
            } catch (Throwable e) {
                Log.e("HeaderAndFooterAdapter", "监听类型错误:" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public class OnClickListener<T> implements View.OnClickListener {
        ClickCallBack<T> callBack;
        T data;

        public OnClickListener(ClickCallBack<T> callBack, T data) {
            this.callBack = callBack;
            this.data = data;
        }

        @Override
        public void onClick(View v) {
            if (callBack == null) {
                return;
            }
            callBack.onClick(data);
        }
    }

    private Object getItem(int position) {
        Object item;
        if (position < head.size()) {
            item = head.get(position);
            return item;
        }

        position = position - head.size();
        if (position < items.size()) {
            item = items.get(position);
            return item;
        }
        position = position - items.size();
        return footer.get(position);

    }

    @Override
    public int getItemCount() {
        return head.size() + items.size() + footer.size();
    }


    @SuppressWarnings("unchecked")
    @Override
    public int getItemViewType(int position) {
//        CommonUtils.log("HeaderAndFooterAdapter position", position, head.size(), items.size(), footer.size());
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
            Log.e("invoke", "error !!!!!!!!!! " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static Object invoke(final Object obj, final String methodName,
                                final Class[] classes) {
        return invoke(obj, methodName, classes, new Object[]{});
    }

    public static Object invoke(final Object obj, final String methodName) {
        return invoke(obj, methodName, new Class[]{}, new Object[]{});
    }
}