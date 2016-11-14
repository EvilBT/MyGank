package xyz.zpayh.mygank.girl;

import dagger.Module;
import dagger.Provides;
import xyz.zpayh.ganknet.service.GankService;

/**
 * 文 件 名: GirlModule
 * 创 建 人: 陈志鹏
 * 创建日期: 2016/10/30 12:30
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */
@Module
public class GirlModule {
    private final GirlContract.View mView;

    public GirlModule(GirlContract.View view) {
        mView = view;
    }

    @Provides
    GirlContract.View provideView(){
        return mView;
    }

    @Provides
    GirlContract.Presenter providePresenter(GankService service){
        return new GirlPresenter(service,mView);
    }
}
