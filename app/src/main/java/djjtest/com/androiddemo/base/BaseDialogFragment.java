package djjtest.com.androiddemo.base;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import djjtest.com.androiddemo.R;

public abstract class BaseDialogFragment<T extends ViewDataBinding> extends AppCompatDialogFragment {


    protected abstract int getLayoutId();

    protected abstract void initView();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.AppTheme_NoActionBar);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    public T binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DataBindingUtil.bind(view);
        initView();
    }

    public BaseDialogFragment setCommitAllowingStateLoss(boolean commitAllowingStateLoss) {
        this.commitAllowingStateLoss = commitAllowingStateLoss;
        return this;
    }

    boolean commitAllowingStateLoss = false;

    @Override
    public void show(FragmentManager manager, String tag) {
        if (!commitAllowingStateLoss) {
            super.show(manager, tag);
            return;
        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

}

