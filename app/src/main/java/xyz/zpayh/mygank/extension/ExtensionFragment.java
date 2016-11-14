package xyz.zpayh.mygank.extension;

import android.os.Bundle;
import android.support.annotation.Nullable;

import xyz.zpayh.mygank.BaseFragment;
import xyz.zpayh.mygank.R;

/**
 * Created by Administrator on 2016/10/31.
 */

public class ExtensionFragment extends BaseFragment {

    public static ExtensionFragment newInstance() {
        ExtensionFragment fragment = new ExtensionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.frag_extension;
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
