package xyz.zpayh.library.net;

import android.content.Context;
import android.support.annotation.NonNull;

import com.socks.library.KLog;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import xyz.zpayh.library.BuildConfig;
import xyz.zpayh.library.util.NetworkUtils;
import xyz.zpayh.retrofit2.adapter.agera.AgeraCallAdapterFactory;

/**
 * Created by Administrator on 2016/11/14.
 */

public class NetRepository {
    private static volatile NetRepository sNetRepository;

    public static NetRepository getNetRepository(@NonNull Context context,@NonNull String baseUrl) {
        if (sNetRepository == null){
            synchronized (NetRepository.class){
                if (sNetRepository == null){
                    sNetRepository = new NetRepository(context,baseUrl);
                }
            }
        }
        return sNetRepository;
    }

    private final OkHttpClient mOkHttpClient;
    private final Retrofit mRetrofit;

    private NetRepository(@NonNull Context context,@NonNull String baseUrl){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (BuildConfig.LOG_DEBUG){
            //log 信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(KLog::i);
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            // 设置 Debug log 模式
            builder.addInterceptor(loggingInterceptor);
        }

        //添加缓存机制
        addCacheInterceptor(context,builder);
        //添加公共参数
        //addPublicQueryParameter(builder);

        // 设置超时重连
        builder.connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS);

        // 错误重连
        builder.retryOnConnectionFailure(true);

        mOkHttpClient = builder.build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(AgeraCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttpClient)
                .build();
    }

    /**
     * 添加缓存机制
     */
    private void addCacheInterceptor(@NonNull Context context, OkHttpClient.Builder builder) {
        // 无网络时，也能显示数据
        File cacheFile = new File(context.getExternalCacheDir(),"gankCache");
        Cache cache = new Cache(cacheFile,1024*1024*50);
        Interceptor cacheInterceptor = chain -> {
            Request request = chain.request();
            if (!NetworkUtils.isOnline(context)){
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            if (NetworkUtils.isOnline(context)) {
                //有网络时，设置缓存超时时间0小时
                int maxAge = 0;
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        //.removeHeader()
                        .build();
            }else{
                int maxStale = 60 * 60 * 24;
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-age=" + maxStale)
                        //.removeHeader()
                        .build();
            }
            return response;
        };
        builder.cache(cache).addNetworkInterceptor(cacheInterceptor);
    }

    private void addPublicQueryParameter(OkHttpClient.Builder builder) {
        // 公共参数
        Interceptor addQueryParameterInterceptor = chain -> {
            Request originalRequest = chain.request();
            Request request;
            HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                    // 添加公共参数
                    //.addQueryParameter("client_id",API.CLIENT_ID)
                    //.addQueryParameter("sign",signature)
                    .build();
            request = originalRequest.newBuilder().url(modifiedUrl).build();
            return chain.proceed(request);
        };

        // 公共参数
        builder.addInterceptor(addQueryParameterInterceptor);
    }

    public OkHttpClient getOkHttpClient(){
        return mOkHttpClient;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
