package xyz.zpayh.mygank.android;

import android.support.annotation.NonNull;

import java.util.List;

import xyz.zpayh.ganknet.data.GankData;
import xyz.zpayh.mygank.BasePresenter;
import xyz.zpayh.mygank.BaseView;
import xyz.zpayh.mygank.girl.GirlContract;

/**
 * 文 件 名: AndroidContract
 * 创 建 人: 陈志鹏
 * 创建日期: 2016/10/31 21:49
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public interface AndroidContract {


    interface View extends BaseView<GirlContract.Presenter> {

        void setData(@NonNull List<GankData> list);

        void addData(@NonNull List<GankData> list);
    }

    interface Presenter extends BasePresenter{
        void loadAndroid(int page, int num);

        void loadMore(int page, int num);
    }

}
