package xyz.zpayh.mygank.ios;

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
 * 文 件 名: IOSPresenter
 * 创 建 人: 陈志鹏
 * 创建日期: 2016/11/14 22:33
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public class IOSPresenter implements IOSContract.Presenter, Updatable{

    private IOSContract.View mView;

    private GankService mGankService;

    private Repository<Result<List<GankData>>> mRepository;

    private boolean mRefreshing;

    public IOSPresenter(GankService gankService, IOSContract.View view) {
        mView = view;
        mGankService = gankService;
        mRefreshing = false;
    }

    private void cancelLoad(){
        if (mRepository != null){
            mRepository.removeUpdatable(this);
            mRepository = null;
        }
    }

    @Override
    public void loadIOS(int page, int num) {
        mRefreshing = true;
        mView.showLoading();
        cancelLoad();

        ResultRepository<GankIoResult> resultRepository = mGankService.getData(API.IOS,page,num);
        Result<List<GankData>> emptyResult = Result.absent();
        mRepository = Repositories.repositoryWithInitialValue(emptyResult)
                .observe(resultRepository)
                .onUpdatesPerLoop()
                .attemptGetFrom(resultRepository)
                .orEnd(Result::failure)
                .thenTransform(input -> Result.absentIfNull(input.getResults()))
                .compile();

        mRepository.addUpdatable(this);
    }

    @Override
    public void loadMore(int page, int num) {
        mRefreshing = false;
        cancelLoad();

        ResultRepository<GankIoResult> resultRepository = mGankService.getData(API.IOS,page,num);
        Result<List<GankData>> emptyResult = Result.absent();
        mRepository = Repositories.repositoryWithInitialValue(emptyResult)
                .observe(resultRepository)
                .onUpdatesPerLoop()
                .attemptGetFrom(resultRepository)
                .orEnd(Result::failure)
                .thenTransform(input -> Result.absentIfNull(input.getResults()))
                .compile();

        mRepository.addUpdatable(this);
    }

    @Override
    public void start() {
        loadIOS(1,10);
    }

    @Override
    public void stop() {

    }

    @Override
    public void update() {
        if (mRefreshing){
            mView.hideLoading();
            mRepository.get().ifSucceededSendTo(mView::setData);
        }else{
            mRepository.get().ifSucceededSendTo(mView::addData);
        }
    }
}
