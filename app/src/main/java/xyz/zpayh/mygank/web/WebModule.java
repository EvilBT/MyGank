package xyz.zpayh.mygank.web;

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
public class WebModule {
    private final WebContract.View mView;

    public WebModule(WebContract.View view) {
        mView = view;
    }

    @Provides
    WebContract.View provideView(){
        return mView;
    }

    @Provides
    WebContract.Presenter providePresenter(GankService service){
        return new WebPresenter(service,mView);
    }
}
