package djjtest.com.androiddemo;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

import djjtest.com.androiddemo.slidelayout.CardTouchListener;

/**
 * Author      :    DongJunJie
 * Date        :    2018/11/30
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class FragmentStateAdapter extends FragmentStatePagerAdapter {
    ArrayList<FragmentAdapter.BaseFragment> fragmentArrayList;
    Fragment currentFragment;
    CardTouchListener listener;

    public FragmentStateAdapter(FragmentManager fm, ArrayList<FragmentAdapter.BaseFragment> fragments) {
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




    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentArrayList.get(position).getTitle();
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        currentFragment = (Fragment) object;
    }

    public Fragment getCurrentPrimaryItem() {
        return currentFragment;
    }
}
