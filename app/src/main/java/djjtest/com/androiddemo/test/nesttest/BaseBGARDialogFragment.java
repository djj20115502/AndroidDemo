package djjtest.com.androiddemo.test.nesttest;


import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import djjtest.com.androiddemo.R;
import djjtest.com.androiddemo.base.BaseDialogFragment;
import djjtest.com.androiddemo.base.HeaderAndFooterAdapter;
import djjtest.com.androiddemo.utils.CommonUtils;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 *
 */
public abstract class BaseBGARDialogFragment<T extends ViewDataBinding> extends BaseDialogFragment<T> {


    public ArrayList<Object> mItems = new ArrayList<>();
    public HeaderAndFooterAdapter multiTypeAdapter;
    int mPageIndex = 1;

    public void setData(List data) {
        if (mPageIndex == 1) {
            mItems.clear();
        } else if (data == null || data.size() == 0) {
            CommonUtils.showToast(getActivity(), "没有更多了");
        }
        if (data != null) {
            mItems.addAll(data);
        }
        if (mItems.size() == 0) {
            setIsEmpty();
        } else {
            setHasData();
        }
        multiTypeAdapter.notifyDataSetChanged();
    }


    @CallSuper
    @Override
    protected void initView() {
        multiTypeAdapter = new HeaderAndFooterAdapter(mItems);
        injectAdapter(multiTypeAdapter);
        initRefreshLayout(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                refresh();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                mPageIndex++;
                updateData();
                return true;
            }
        });
    }

    BGARefreshLayout mRefreshLayout;

    /**
     * 初始化上拉加载、下拉刷新控件
     *
     * @param bgDelegate
     */
    protected void initRefreshLayout(BGARefreshLayout.BGARefreshLayoutDelegate bgDelegate) {
        View view = getView().findViewById(R.id.bg_refresh_root);
        if (!(view instanceof BGARefreshLayout)) {
            return;
        }
        mRefreshLayout = (BGARefreshLayout) view;
        mRefreshLayout.setDelegate(bgDelegate);
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getActivity(), true);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    public void refresh() {
        mItems.clear();
        mPageIndex = 1;
        updateData();
    }

    protected void stopRefreshAll() {
        if (mRefreshLayout == null) {
            return;
        }
        mRefreshLayout.endLoadingMore();
        mRefreshLayout.endRefreshing();
    }

    public abstract MultiTypeAdapter injectAdapter(MultiTypeAdapter multiTypeAdapter);


    protected abstract void updateData();

    /**
     * 数据为空
     */
    public abstract void setIsEmpty();

    /**
     * 数据非空
     */
    public abstract void setHasData();

}
