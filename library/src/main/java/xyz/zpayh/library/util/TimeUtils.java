package xyz.zpayh.library.util;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2016/11/16.
 */

public class TimeUtils {

    public static String format(@NonNull Date date,@NonNull String formatString){
        final SimpleDateFormat format = new SimpleDateFormat(formatString, Locale.CHINA);
        return format.format(date);
    }
}
