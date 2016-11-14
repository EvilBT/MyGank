package xyz.zpayh.mygank.data;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import xyz.zpayh.ganknet.service.GankService;
import xyz.zpayh.library.LibUtils;

/**
 * 文 件 名: DataRepositoryModule
 * 创 建 人: 陈志鹏
 * 创建日期: 2016/10/30 11:45
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */
@Module
public class DataRepositoryModule {
    //private final Retrofit mRetrofit;
   // private final OkHttpClient mHttpClient;

    public DataRepositoryModule(){
        /*mHttpClient = new OkHttpClient.Builder()
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(AgeraCallAdapterFactory.create())
                .client(mHttpClient)
                .build();

        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(context,mHttpClient)
                .setDownsampleEnabled(true)//向下采样
                .build();
        Fresco.initialize(context,config);*/
    }

    @Provides
    Retrofit provideRetrofit(){
        //return mRetrofit;
        return LibUtils.getRetrofit();
    }

    @Singleton
    @Provides
    GankService provideGankService(){
        //return mRetrofit.create(GankService.class);
        return LibUtils.getRetrofit().create(GankService.class);
    }
}
