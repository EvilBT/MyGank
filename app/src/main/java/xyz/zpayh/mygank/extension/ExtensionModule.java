package xyz.zpayh.mygank.extension;

import dagger.Module;
import dagger.Provides;
import xyz.zpayh.ganknet.service.GankService;

/**
 * 文 件 名: ExtensionModule
 * 创 建 人: 陈志鹏
 * 创建日期: 2016/11/14 22:33
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */
@Module
public class ExtensionModule {
    private final ExtensionContract.View mView;

    public ExtensionModule(ExtensionContract.View view) {
        mView = view;
    }

    @Provides
    ExtensionContract.View provideView(){
        return mView;
    }

    @Provides
    ExtensionContract.Presenter providePresenter(GankService service){
        return new ExtensionPresenter(service,mView);
    }
}
