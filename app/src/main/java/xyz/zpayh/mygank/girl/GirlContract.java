package xyz.zpayh.mygank.girl;

import android.support.annotation.NonNull;

import java.util.List;

import xyz.zpayh.ganknet.data.GankData;
import xyz.zpayh.mygank.BasePresenter;
import xyz.zpayh.mygank.BaseView;

/**
 * 文 件 名: GirlContract
 * 创 建 人: 陈志鹏
 * 创建日期: 2016/10/30 12:05
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public interface GirlContract {

    interface View extends BaseView<Presenter>{

        void setData(@NonNull List<GankData> list);

        void addData(@NonNull List<GankData> list);
    }

    interface Presenter extends BasePresenter{
        void loadGirls(int page, int num);

        void loadMore(int page, int num);
    }
}
