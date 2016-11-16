package xyz.zpayh.mygank.ios;

import dagger.Module;
import dagger.Provides;
import xyz.zpayh.ganknet.service.GankService;

/**
 * 文 件 名: IOSModule
 * 创 建 人: 陈志鹏
 * 创建日期: 2016/11/14 22:33
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */
@Module
public class IOSModule {
    private final IOSContract.View mView;

    public IOSModule(IOSContract.View view) {
        mView = view;
    }

    @Provides
    IOSContract.View provideView(){
        return mView;
    }

    @Provides
    IOSContract.Presenter providePresenter(GankService service){
        return new IOSPresenter(service,mView);
    }
}
