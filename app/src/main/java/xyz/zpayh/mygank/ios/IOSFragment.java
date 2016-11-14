package xyz.zpayh.mygank.ios;

import android.os.Bundle;
import android.support.annotation.Nullable;

import xyz.zpayh.mygank.BaseFragment;
import xyz.zpayh.mygank.R;

/**
 * Created by Administrator on 2016/10/31.
 */

public class IOSFragment extends BaseFragment {

    public static IOSFragment newInstance() {
        IOSFragment fragment = new IOSFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.frag_ios;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
