package xyz.zpayh.library.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.io.File;

/**
 * Created by Administrator on 2016/11/15.
 */

public class FileUtils {


    public static void scanMedia(@NonNull Context context, @NonNull File file){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri content = Uri.fromFile(file);
        mediaScanIntent.setData(content);
        context.sendBroadcast(mediaScanIntent);
    }
}
