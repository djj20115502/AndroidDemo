package djjtest.com.androiddemo;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Author      :    DongJunJie
 * Date        :    2018/11/30
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    ArrayList<BaseFragment> fragmentArrayList;

    public FragmentAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments) {
        super(fm);
        fragmentArrayList = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);

    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public static abstract class BaseFragment extends Fragment {
        protected abstract CharSequence getTitle();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentArrayList.get(position).getTitle();
    }
}
