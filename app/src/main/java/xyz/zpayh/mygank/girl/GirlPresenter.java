package xyz.zpayh.mygank.girl;

import android.support.annotation.NonNull;

import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Updatable;

import java.util.List;

import xyz.zpayh.ganknet.api.API;
import xyz.zpayh.ganknet.data.GankData;
import xyz.zpayh.ganknet.data.GankIoResult;
import xyz.zpayh.ganknet.service.GankService;
import xyz.zpayh.retrofit2.adapter.agera.ResultRepository;

/**
 * 文 件 名: GirlPresenter
 * 创 建 人: 陈志鹏
 * 创建日期: 2016/10/30 12:08
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public class GirlPresenter implements GirlContract.Presenter, Updatable{

    private GirlContract.View mGirlView;

    private boolean mRefresh;

    private GankService mGankService;

    //private ResultRepository<GankIoResult> mRepository;

    private Repository<Result<List<GankData>>> mRepository;

    GirlPresenter(@NonNull GankService gankService,
                  GirlContract.View view){
        this.mGankService = gankService;
        this.mGirlView = view;
    }

    @Override
    public void loadGirls(int page, int num) {
        mGirlView.showLoading();
        mRefresh = true;

        cancelRequest();

        ResultRepository<GankIoResult> resultRepository = mGankService.getData(API.GIRLS,page,num);

        Result<List<GankData>> emptyResult = Result.absent();

        mRepository = Repositories.repositoryWithInitialValue(emptyResult)
                .observe(resultRepository)
                .onUpdatesPerLoop()
                .attemptGetFrom(resultRepository)
                .orEnd(input -> Result.absent())
                .thenTransform(input -> Result.absentIfNull(input.getResults()))
                .compile();

        mRepository.addUpdatable(this);
    }

    @Override
    public void loadMore(int page, int num) {
        mRefresh = false;
        cancelRequest();
        ResultRepository<GankIoResult> resultRepository = mGankService.getData(API.GIRLS,page,num);

        Result<List<GankData>> emptyResult = Result.absent();

        mRepository = Repositories.repositoryWithInitialValue(emptyResult)
                .observe(resultRepository)
                .onUpdatesPerLoop()
                .attemptGetFrom(resultRepository)
                .orEnd(input -> Result.absent())
                .thenTransform(input -> Result.absentIfNull(input.getResults()))
                .compile();
        mRepository.addUpdatable(this);
    }

    @Override
    public void start() {
        loadGirls(1,10);
    }

    @Override
    public void stop() {
        mRepository.removeUpdatable(this);
    }

    @Override
    public void update() {
        if (mRefresh) {
            mGirlView.hideLoading();
            mRepository.get().ifSucceededSendTo(mGirlView::setData);
        }else{
            mRepository.get().ifSucceededSendTo(mGirlView::addData);
        }
    }

    private void cancelRequest() {
        if (mRepository != null){
            mRepository.removeUpdatable(this);
            mRepository = null;
        }
    }
}
