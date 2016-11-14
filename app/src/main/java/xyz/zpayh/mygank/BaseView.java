package xyz.zpayh.mygank;

import android.support.annotation.NonNull;

/**
 * 文 件 名: BaseView
 * 创 建 人: 陈志鹏
 * 创建日期: 2016/10/30 11:26
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public interface BaseView<T> {

    void showLoading();

    void hideLoading();

    void showTips(@NonNull String tips);
}
