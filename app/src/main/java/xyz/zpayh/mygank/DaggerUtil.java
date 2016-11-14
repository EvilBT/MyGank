package xyz.zpayh.mygank;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import xyz.zpayh.mygank.data.DaggerDataRepositoryComponent;
import xyz.zpayh.mygank.data.DataRepositoryComponent;

/**
 * Created by Administrator on 2016/11/14.
 */

public class DaggerUtil {
    private static Application sApplication;
    private static Context sContext;

    private static DataRepositoryComponent component;

    private static boolean initComponent;

    static {
        initComponent = false;
    }

    static void setApplication(@NonNull Application application){
        sApplication = application;
    }

    static void setContext(@NonNull Context context){
        sContext = context;
    }

    synchronized static void initDataRepositoryComponent(){
        if (initComponent){
            return;
        }
        initComponent = true;
        component = DaggerDataRepositoryComponent
                .builder()
                .build();
    }

    public static DataRepositoryComponent getRepositoryComponent() {
        if (!initComponent){
            throw new IllegalStateException("Component还未初始化");
        }
        return component;
    }

    public static Context getContext(){
        return sContext;
    }
}
