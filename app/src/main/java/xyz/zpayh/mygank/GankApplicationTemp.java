package xyz.zpayh.mygank;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;


/**
 * Created by Sherlock on 2016/10/30.
 */

public class GankApplicationTemp extends TinkerApplication {

    public GankApplicationTemp() {
        super(ShareConstants.TINKER_ENABLE_ALL,
                "xyz.zpayh.mygank.GankApplicationLike");
    }
}
