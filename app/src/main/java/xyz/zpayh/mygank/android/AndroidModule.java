package xyz.zpayh.mygank.android;

import dagger.Module;
import dagger.Provides;
import xyz.zpayh.ganknet.service.GankService;
import xyz.zpayh.mygank.girl.GirlContract;
import xyz.zpayh.mygank.girl.GirlPresenter;

/**
 * 文 件 名: AndroidModule
 * 创 建 人: 陈志鹏
 * 创建日期: 2016/11/14 22:33
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */
@Module
public class AndroidModule {
    private final AndroidContract.View mView;

    public AndroidModule(AndroidContract.View view) {
        mView = view;
    }

    @Provides
    AndroidContract.View provideView(){
        return mView;
    }

    @Provides
    AndroidContract.Presenter providePresenter(GankService service){
        return new AndroidPresenter(service,mView);
    }
}
