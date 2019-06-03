package djjtest.com.androiddemo.test.nesttest;

import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.databinding.FragmentNestTestBinding;
import djjtest.com.androiddemo.utils.CommonUtils;
import djjtest.com.androiddemo.view.CustomOnTextChooseDialog;
import me.drakeet.multitype.MultiTypeAdapter;

public class TestNest extends BaseBGARDialogFragment<FragmentNestTestBinding> {


    public static void invoke(FragmentManager fragmentManager) {
        TestNest testNest = new TestNest();
        testNest.show(fragmentManager, "testNest");
    }


    @Override
    public MultiTypeAdapter injectAdapter(MultiTypeAdapter multiTypeAdapter) {
        CustomOnTextChooseDialog.OneTextHolder.inject(multiTypeAdapter);
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        binding.setHolder(this);
        for (int i = 0; i < 5; i++) {
            mItems.add(new CustomOnTextChooseDialog.OneTextHolder.Bean());
        }
//        binding.rv.setAdapter(multiTypeAdapter);
        stopRefreshAll();
    }

    @Override
    protected void updateData() {
        CommonUtils.log("updateData", mPageIndex);
        binding.root.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopRefreshAll();
                if(mPageIndex==1){
                    mItems.clear();
                }
                ArrayList a=new ArrayList();
                for (int i = 0; i < 5; i++) {
                    a.add(new CustomOnTextChooseDialog.OneTextHolder.Bean());

                }
                setData(a);
            }
        }, 500);


    }

    @Override
    public void setIsEmpty() {

    }

    @Override
    public void setHasData() {

    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nest_test;
    }

    public void onClickBack() {
        dismiss();
    }
}
