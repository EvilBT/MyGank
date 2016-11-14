package xyz.zpayh.mygank;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 文 件 名: BaseFragment
 * 创 建 人: 陈志鹏
 * 创建日期: 2016/10/30 00:48
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public abstract class BaseFragment extends Fragment{

    @NonNull
    protected View mRootView;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutResID(),container,false);
        initView(savedInstanceState);
        initListener();
        initData();
        return mRootView;
    }

    protected abstract int getLayoutResID();

    protected abstract void initView(@Nullable Bundle savedInstanceState);

    protected abstract void initListener();

    protected abstract void initData();

    @SuppressWarnings("unchecked")
    protected <T> T findView(@IdRes int id){
        return (T) mRootView.findViewById(id);
    }
}
