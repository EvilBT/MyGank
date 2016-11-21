package xyz.zpayh.mygank.wxapi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.socks.library.KLog;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

import xyz.zpayh.library.LibUtils;
import xyz.zpayh.mygank.R;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wxentry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        LibUtils.getWXApi()
                .handleIntent(getIntent(),this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        KLog.d(baseReq.transaction);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        KLog.d(baseResp.transaction);
    }
}
