package xyz.zpayh.mygank;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Sherlock on 2016/10/30.
 */

public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());

        initView(savedInstanceState);
        initListener();
        initData();
    }

    protected abstract int getLayoutResID();

    protected abstract void initView(@Nullable Bundle savedInstanceState);

    protected abstract void initListener();

    protected abstract void initData();

    @SuppressWarnings("unchecked")
    protected <T> T findView(@IdRes int id){
        return (T) findViewById(id);
    }
}
