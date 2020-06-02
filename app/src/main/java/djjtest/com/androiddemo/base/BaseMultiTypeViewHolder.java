package djjtest.com.androiddemo.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

import djjtest.com.androiddemo.utils.CommonUtils;
import me.drakeet.multitype.ItemViewProvider;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Author      :    DongJunJie
 * Date        :    2018/12/19
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public abstract class BaseMultiTypeViewHolder<T> extends RecyclerView.ViewHolder {

    public static <E> MultiTypeAdapter inject(MultiTypeAdapter adapter, final int id, final Class<? extends BaseMultiTypeViewHolder<E>> holder) {
        Class<E> entityClass = (Class<E>) ((ParameterizedType) holder.getGenericSuperclass()).getActualTypeArguments()[0];
        adapter.register(entityClass, new ItemViewProvider<E, BaseMultiTypeViewHolder<E>>() {
            @NonNull
            @Override
            protected BaseMultiTypeViewHolder<E> onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
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
            protected void onBindViewHolder(@NonNull BaseMultiTypeViewHolder<E> holder, @NonNull E t) {
                holder.clearViewState();
                holder.bind(t);
            }
        });
        return adapter;
    }

    public BaseMultiTypeViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(T t);

    public abstract void clearViewState();

}

