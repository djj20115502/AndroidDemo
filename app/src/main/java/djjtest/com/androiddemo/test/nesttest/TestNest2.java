package djjtest.com.androiddemo.test.nesttest;

import android.support.v4.app.FragmentManager;
import android.widget.ImageView;

import java.util.ArrayList;

import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.databinding.FragmentNestTest2Binding;
import djjtest.com.androiddemo.databinding.FragmentNestTestBinding;
import djjtest.com.androiddemo.utils.CommonUtils;
import djjtest.com.androiddemo.view.CustomOnTextChooseDialog;
import me.drakeet.multitype.MultiTypeAdapter;

public class TestNest2 extends BaseBGARDialogFragment<FragmentNestTest2Binding> {


    public static void invoke(FragmentManager fragmentManager) {
        TestNest2 testNest = new TestNest2();
        testNest.show(fragmentManager, "testNest");
    }


    @Override
    public MultiTypeAdapter injectAdapter(MultiTypeAdapter multiTypeAdapter) {

        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        binding.setHolder(this);
        for (int i = 0; i < 20; i++) {
            mItems.add(new CustomOnTextChooseDialog.OneTextHolder.Bean());
        }

        stopRefreshAll();

        for (int i = 0; i <5; i++) {
            ImageView view =new ImageView(getActivity());
            view.setImageResource(R.drawable.girl);
            binding.linear.addView(view );
        }
    }

    @Override
    protected void updateData() {
        CommonUtils.log("updateData", mPageIndex);
        binding.root.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopRefreshAll();
                if(mPageIndex==1){
                    binding.linear.removeAllViews();
                }


                for (int i = 0; i <10; i++) {
                    ImageView view =new ImageView(getActivity());
                    view.setImageResource(R.drawable.girl);
                    binding.linear.addView(view );



                }

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
        return R.layout.fragment_nest_test2;
    }

    public void onClickBack() {
        dismiss();
    }
}
