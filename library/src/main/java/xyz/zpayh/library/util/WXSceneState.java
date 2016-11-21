package xyz.zpayh.library.util;

import android.support.annotation.IntDef;

/**
 * Created by Administrator on 2016/11/21.
 */

@IntDef({WXSceneState.SESSION,WXSceneState.TIME_LINE,WXSceneState.FAVORITE})
public @interface WXSceneState {
    int SESSION = 0;
    int TIME_LINE = 1;
    int FAVORITE = 2;
}
