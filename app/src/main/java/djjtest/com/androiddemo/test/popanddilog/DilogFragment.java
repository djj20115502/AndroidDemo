package djjtest.com.androiddemo.test.popanddilog;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.utils.CommonUtils;
import djjtest.com.androiddemo.view.ChoosePopWindow;

public class DilogFragment extends AppCompatDialogFragment {


    public static void invoke(FragmentManager fragmentManager) {
        DilogFragment roadBookDetailFragment = new DilogFragment();
        roadBookDetailFragment.show(fragmentManager, "roadBookDetailFragment");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_fragment_one_butotn, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();
        getView().findViewById(R.id.right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ChoosePopWindow.OneTextBean> list = new ArrayList<>();
                list.add(new ChoosePopWindow.OneTextBean("sdf", "sdf"));
                list.add(new ChoosePopWindow.OneTextBean("sdf", "sdf"));
                list.add(new ChoosePopWindow.OneTextBean("sdf", "sdf"));
                list.add(new ChoosePopWindow.OneTextBean("sdf", "sdf"));
                list.add(new ChoosePopWindow.OneTextBean("sdf", "sdf"));
                new ChoosePopWindow.Builder()
                        .context(getActivity())
                        .showData(list)
                        .callBack(new ChoosePopWindow.CallBack() {
                            @Override
                            public void onResult(Object o) {
                                CommonUtils.showToast(getActivity(), o.toString());
                            }
                        })
                        .build()

                        .showAtLocation(getView().findViewById(R.id.right), Gravity.NO_GRAVITY, 0, 0);
            }
        });

    }
}
