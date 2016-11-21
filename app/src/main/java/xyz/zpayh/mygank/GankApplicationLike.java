package xyz.zpayh.mygank;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import xyz.zpayh.ganknet.api.API;
import xyz.zpayh.library.LibUtils;

/**
 * Created by Administrator on 2016/11/14.
 */
@SuppressWarnings("unused")
@DefaultLifeCycle(
        application = ".GankApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL
)
public class GankApplicationLike extends DefaultApplicationLike{
    public GankApplicationLike(Application application,
                               int tinkerFlags,
                               boolean tinkerLoadVerifyFlag,
                               long applicationStartElapsedTime,
                               long applicationStartMillisTime,
                               Intent tinkerResultIntent,
                               Resources[] resources,
                               ClassLoader[] classLoader,
                               AssetManager[] assetManager) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent, resources, classLoader, assetManager);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);

        TinkerInstaller.install(this);

       //KLog.init(BuildConfig.LOG_DEBUG,"Sherlock");
        //if (LeakCanary.isInAnalyzerProcess(getApplication())) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
        //    return;
        //}
        //LeakCanary.install(getApplication());

        LibUtils.initRetrofit(getApplication(), API.BASE_URL);

        DaggerUtil.setApplication(getApplication());
        DaggerUtil.setContext(getApplication());
        DaggerUtil.initDataRepositoryComponent();
    }
}
