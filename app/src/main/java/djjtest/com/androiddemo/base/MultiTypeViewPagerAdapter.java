package djjtest.com.androiddemo.base;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;

import djjtest.com.androiddemo.utils.CommonUtils;

/**
 * Author      :    DongJunJie
 * Date        :    2019/3/14
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class MultiTypeViewPagerAdapter extends PagerAdapter {

    public ArrayList<Object> mDatas;
    LayoutInflater inflater;


    public MultiTypeViewPagerAdapter(Context context, ArrayList<Object> mDatas) {
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        Object data = mDatas.get(position);
        ItemViewProvider itemViewProvider = hashMap.get(data.getClass());
        if (itemViewProvider == null) {
            return null;
        }
        RecyclerView.ViewHolder vh = itemViewProvider.onCreateViewHolder(inflater, container);
        container.addView(vh.itemView);
        vh.itemView.setTag(data.getClass().getSimpleName());
        itemViewProvider.onBindViewHolder(vh, data);
        CommonUtils.log("instantiateItem", data.getClass().getSimpleName(), data);
        return vh.itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        CommonUtils.log("destroyItem", ((View) object).getTag());

    }

    final HashMap<Class, ItemViewProvider> hashMap = new HashMap<>();

    public <E> void inject(final int id, final Class<? extends BaseMultiTypeViewHolder<E>> holder) {
        Class<E> entityClass = (Class<E>) ((ParameterizedType) holder.getGenericSuperclass()).getActualTypeArguments()[0];
        hashMap.put(entityClass, new ItemViewProvider<E, BaseMultiTypeViewHolder<E>>() {
            @NonNull
            @Override
            public BaseMultiTypeViewHolder<E> onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
                View v = inflater.inflate(id, parent, false);
                BaseMultiTypeViewHolder<E> rt = null;
                try {
                    Constructor<? extends BaseMultiTypeViewHolder<E>> constructor = holder.getConstructor(View.class);
                    constructor.setAccessible(true);
                    rt = constructor.newInstance(v);
                } catch (NoSuchMethodException e) {
                    CommonUtils.log(e.getMessage());
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    CommonUtils.log(e.getMessage());
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    CommonUtils.log(e.getMessage());
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    CommonUtils.log(e.getMessage());
                    e.printStackTrace();
                }
                return rt;
            }

            @Override
            public void onBindViewHolder(@NonNull BaseMultiTypeViewHolder<E> holder, @NonNull E t) {
                holder.bind(t);
            }
        });
    }

    public abstract class ItemViewProvider<T, V extends RecyclerView.ViewHolder> {

        @NonNull
        public abstract V onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent);

        public abstract void onBindViewHolder(@NonNull V holder, @NonNull T t);

    }
}
