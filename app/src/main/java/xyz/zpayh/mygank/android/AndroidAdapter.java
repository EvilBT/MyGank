package xyz.zpayh.mygank.android;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import xyz.zpayh.ganknet.data.GankData;
import xyz.zpayh.imageloader.util.FrescoUtil;
import xyz.zpayh.mygank.R;

/**
 * 文 件 名: AndroidAdapter
 * 创 建 人: 陈志鹏
 * 创建日期: 2016/11/14 23:15
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public class AndroidAdapter extends BaseQuickAdapter<GankData,BaseViewHolder> {

    private final int mViewWidth;

    public AndroidAdapter(int viewWidth) {
        super(R.layout.item_android,null);
        this.mViewWidth = viewWidth;
    }

    @Override
    protected void convert(BaseViewHolder helper, GankData gankData) {
        List<String> images = gankData.getImages();
        if (!images.isEmpty()){
            SimpleDraweeView view = helper.getView(R.id.sd_image);
            view.setVisibility(View.VISIBLE);
            FrescoUtil.setResizeImage(view,images.get(0),mViewWidth);
        }else{
            SimpleDraweeView view = helper.getView(R.id.sd_image);
            view.setVisibility(View.GONE);
        }
        helper.setText(R.id.title,gankData.getDesc())
                .setText(R.id.user,gankData.getWho());
    }
}
