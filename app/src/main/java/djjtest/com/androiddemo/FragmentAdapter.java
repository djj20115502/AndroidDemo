package djjtest.com.androiddemo;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

import djjtest.com.androiddemo.slidelayout.CardTouchListener;

/**
 * Author      :    DongJunJie
 * Date        :    2018/11/30
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    ArrayList<BaseFragment> fragmentArrayList;
    Fragment currentFragment;
    CardTouchListener listener;

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
        public String title = "";

        public BaseFragment setTitle(String title) {
            this.title = title;
            return this;
        }

        public CharSequence getTitle() {
            return title;
        }
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
