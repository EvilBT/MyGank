package xyz.zpayh.imageloader;

import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/11/14.
 */

public class ImageLoader {

    public static void initFresco(@NonNull Context context,@NonNull OkHttpClient okHttpClient){
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(context,okHttpClient)
                .setDownsampleEnabled(true)//设置向下采样
                .build();
        Fresco.initialize(context,config);
    }
}
