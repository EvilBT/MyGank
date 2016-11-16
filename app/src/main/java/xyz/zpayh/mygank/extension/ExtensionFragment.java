package xyz.zpayh.mygank.extension;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;

import xyz.zpayh.bus.AgeraBus;
import xyz.zpayh.ganknet.data.GankData;
import xyz.zpayh.library.util.ScreenUtils;
import xyz.zpayh.mygank.BaseFragment;
import xyz.zpayh.mygank.DaggerUtil;
import xyz.zpayh.mygank.R;
import xyz.zpayh.mygank.android.AndroidAdapter;

/**
 * Created by Administrator on 2016/10/31.
 */

public class ExtensionFragment extends BaseFragment implements ExtensionContract.View{

    @Inject
    ExtensionContract.Presenter mPresenter;

    private SwipeRefreshLayout mRefreshList;

    private RecyclerView mList;

    private AndroidAdapter mAdapter;

    public static ExtensionFragment newInstance() {
        ExtensionFragment fragment = new ExtensionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.frag_extension;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

        DaggerExtensionComponent.builder()
                .extensionModule(new ExtensionModule(this))
                .dataRepositoryComponent(DaggerUtil
                        .getRepositoryComponent())
                .build()
                .inject(this);

        mRefreshList = findView(R.id.srl_list);
        mRefreshList.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary);
        mList = findView(R.id.rv_list);
        mList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void initListener() {
        mRefreshList.setOnRefreshListener(()->mPresenter.loadExtension(1,10));

        mList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                final GankData data = mAdapter.getItem(position);
                AgeraBus.getDefault()
                        .post(data);
            }
        });
    }

    @Override
    protected void initData() {
        mAdapter = new AndroidAdapter(ScreenUtils.getScreenSize(getContext())[0]);
        mAdapter.openLoadMore(10);
        mAdapter.setOnLoadMoreListener(()->
                mPresenter.loadMore(mAdapter.getItemCount()/10+1,10));
        mList.setAdapter(mAdapter);
    }

    @Override
    public void setData(@NonNull List<GankData> list) {
        KLog.d("数据"+list.size());
        mAdapter.setNewData(list);
    }

    @Override
    public void addData(@NonNull List<GankData> list) {
        KLog.d("数据"+list.size());
        mAdapter.addData(list);
    }

    @Override
    public void showLoading() {
        mRefreshList.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mRefreshList.setRefreshing(false);
    }

    @Override
    public void showTips(@NonNull String tips) {
        Snackbar.make(mRootView,tips,Snackbar.LENGTH_SHORT).show();
    }
}
