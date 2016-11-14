package xyz.zpayh.mygank.data;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;
import xyz.zpayh.ganknet.service.GankService;
import xyz.zpayh.mygank.ApplicationModule;

/**
 * 文 件 名: DataRepositoryComponent
 * 创 建 人: 陈志鹏
 * 创建日期: 2016/10/30 11:54
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */
@Singleton
@Component(modules = {ApplicationModule.class, DataRepositoryModule.class})
public interface DataRepositoryComponent {

    Retrofit getRetrofit();

    GankService getGankService();
}
