package xyz.zpayh.library;

import android.content.Context;
import android.support.annotation.NonNull;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import xyz.zpayh.library.net.NetRepository;

/**
 * Created by Administrator on 2016/11/14.
 */

public class LibUtils {
    private LibUtils(){
        throw new IllegalStateException("不可被实例化");
    }

    public static final String WX_API_ID = "你的API id";

    private static IWXAPI api;

    private static boolean initRetrofit;
    private static NetRepository netRepository;
    static {
        initRetrofit = false;
    }

   /* public static void init(@NonNull Context context, @NonNull String baseUrl){
        initRetrofit(context, baseUrl);
    }*/

    public static void initAPI(@NonNull Context context){
        api = WXAPIFactory.createWXAPI(context,WX_API_ID,true);
        api.registerApp(WX_API_ID);
    }

    public static void initRetrofit(@NonNull Context context, @NonNull String baseUrl){
        if (initRetrofit){
            return;
        }
        netRepository = NetRepository.getNetRepository(context, baseUrl);
        initRetrofit = true;
    }

    public static IWXAPI getWXApi(){
        return api;
    }

    public static Retrofit getRetrofit(){
        if (!initRetrofit){
            throw  new IllegalStateException("NetRepository 还未被初始化，请先执行LibUtils#initRetrofit");
        }
        return netRepository.getRetrofit();
    }

    public static OkHttpClient getOkHttpClient(){
        if (!initRetrofit){
            throw  new IllegalStateException("NetRepository 还未被初始化，请先执行LibUtils#initRetrofit");
        }
        return netRepository.getOkHttpClient();
    }
}
