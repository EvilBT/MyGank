package xyz.zpayh.mygank;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.google.android.agera.Updatable;

import xyz.zpayh.bus.AgeraBus;
import xyz.zpayh.ganknet.api.API;
import xyz.zpayh.ganknet.data.GankData;
import xyz.zpayh.imageloader.ImageLoader;
import xyz.zpayh.library.LibUtils;
import xyz.zpayh.mygank.android.AndroidFragment;
import xyz.zpayh.mygank.extension.ExtensionFragment;
import xyz.zpayh.mygank.girl.GirlFragment;
import xyz.zpayh.mygank.ios.IOSFragment;
import xyz.zpayh.mygank.web.WebFragment;
import xyz.zpayh.mygank.webview.WebViewActivity;

public class MainActivity extends BaseActivity implements Updatable{

    private BottomNavigationView mBottomNavigationView;

    private GirlFragment mGirlFragment;
    private AndroidFragment mAndroidFragment;
    private IOSFragment miOSFragment;
    private WebFragment mWebFragment;
    private ExtensionFragment mExtensionFragment;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        ImageLoader.initFresco(this, LibUtils.getOkHttpClient());
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBottomNavigationView = findView(R.id.bottom_nav);
        initFragment(savedInstanceState);
    }

    @Override
    protected void initListener() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            updateTab(item.getTitle(),item.getItemId());
            return true;
        });
    }

    private void updateTab(@NonNull CharSequence title, int itemId) {
        setTitle(title);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        switch (itemId){
            case R.id.tab_girl:
                if (!mGirlFragment.isAdded()){
                    transaction.add(R.id.content,mGirlFragment, API.GIRLS);
                }
                transaction.show(mGirlFragment);
                break;
            case R.id.tab_android:
                if (!mAndroidFragment.isAdded()){
                    transaction.add(R.id.content,mAndroidFragment,API.ANDROID);
                }
                transaction.show(mAndroidFragment);
                break;
            case R.id.tab_ios:
                if (!miOSFragment.isAdded()){
                    transaction.add(R.id.content,miOSFragment,API.IOS);
                }
                transaction.show(miOSFragment);
                break;
            case R.id.tab_web:
                if (!mWebFragment.isAdded()){
                    transaction.add(R.id.content,mWebFragment,API.WEB);
                }
                transaction.show(mWebFragment);
                break;
            case R.id.tab_extension:
                if (!mExtensionFragment.isAdded()){
                    transaction.add(R.id.content,mExtensionFragment,API.EXTENSION);
                }
                transaction.show(mExtensionFragment);
                break;
        }
        transaction.commitNowAllowingStateLoss();
    }

    @Override
    protected void initData() {
       updateTab(getString(R.string.girl),R.id.tab_girl);
    }

    private void initFragment(Bundle savedInstanceState) {
        mGirlFragment = (GirlFragment) getSupportFragmentManager().findFragmentByTag(API.GIRLS);
        if (mGirlFragment == null){
            mGirlFragment = GirlFragment.newInstance();
        }
        mAndroidFragment = (AndroidFragment) getSupportFragmentManager().findFragmentByTag(API.ANDROID);
        if (mAndroidFragment == null){
            mAndroidFragment = AndroidFragment.newInstance();
        }
        miOSFragment = (IOSFragment) getSupportFragmentManager().findFragmentByTag(API.IOS);
        if (miOSFragment == null){
            miOSFragment = IOSFragment.newInstance();
        }
        mWebFragment = (WebFragment) getSupportFragmentManager().findFragmentByTag(API.WEB);
        if (mWebFragment == null){
            mWebFragment = WebFragment.newInstance();
        }
        mExtensionFragment = (ExtensionFragment) getSupportFragmentManager().findFragmentByTag(API.EXTENSION);
        if (mExtensionFragment == null){
            mExtensionFragment = ExtensionFragment.newInstance();
        }
    }

    private void hideFragments(FragmentTransaction transaction){
        transaction.hide(mGirlFragment);
        transaction.hide(mAndroidFragment);
        transaction.hide(miOSFragment);
        transaction.hide(mWebFragment);
        transaction.hide(mExtensionFragment);
    }


    @Override
    protected void onStart() {
        super.onStart();
        AgeraBus.getDefault()
                .addUpdatable(this, GankData.class);
    }

    @Override
    protected void onStop() {
        super.onStop();
        AgeraBus.getDefault()
                .removeUpdatable(this, GankData.class);
    }

    @Override
    public void update() {
        AgeraBus.getDefault().getSupplier(GankData.class)
                .get().ifSucceededSendTo(value ->
            WebViewActivity.start(this,value.getUrl(),value.getDesc()));
    }
}
