package xyz.zpayh.mygank.girl;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;

import xyz.zpayh.ganknet.data.GankData;
import xyz.zpayh.library.util.ScreenUtils;
import xyz.zpayh.mygank.BaseFragment;
import xyz.zpayh.mygank.DaggerUtil;
import xyz.zpayh.mygank.R;


public class GirlFragment extends BaseFragment implements GirlContract.View{

    @Inject
    GirlContract.Presenter mPresenter;

    private SwipeRefreshLayout mRefreshList;

    private RecyclerView mList;

    private GirlAdapter mAdapter;

    public GirlFragment() {

    }


    public static GirlFragment newInstance() {
        GirlFragment fragment = new GirlFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.frag_girl;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        DaggerGirlComponent.builder()
                .girlModule(new GirlModule(this))
                .dataRepositoryComponent(DaggerUtil
                        .getRepositoryComponent())
                .build()
                .inject(this);

        mRefreshList = findView(R.id.srl_list);
        mRefreshList.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary);
        mList = findView(R.id.rv_list);
        mList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mAdapter = new GirlAdapter(ScreenUtils.getScreenSize(getContext())[0]/2);
        mAdapter.openLoadMore(10);
        mList.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        mRefreshList.setOnRefreshListener(()->mPresenter.loadGirls(1,10));
        mAdapter.setOnLoadMoreListener(()->
                mPresenter.loadMore(mAdapter.getItemCount()/10+1,10));
    }

    @Override
    protected void initData() {

    }

    //=====================View==================


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
