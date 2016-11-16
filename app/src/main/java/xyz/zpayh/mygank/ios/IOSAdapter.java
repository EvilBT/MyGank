package xyz.zpayh.mygank.ios;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import xyz.zpayh.ganknet.data.GankData;
import xyz.zpayh.imageloader.util.FrescoUtil;
import xyz.zpayh.library.util.TimeUtils;
import xyz.zpayh.mygank.R;

/**
 * 文 件 名: IOSAdapter
 * 创 建 人: 陈志鹏
 * 创建日期: 2016/10/30 22:38
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public class IOSAdapter extends BaseQuickAdapter<GankData,BaseViewHolder>{

    private final int mViewWidth;

    public IOSAdapter(int viewWidth) {
        super(R.layout.item_ios, null);
        this.mViewWidth = viewWidth;
    }

    @Override
    protected void convert(BaseViewHolder helper, GankData gankData) {
        List<String> images = gankData.getImages();
        if (images != null && !images.isEmpty()){
            SimpleDraweeView view = helper.getView(R.id.sd_image);
            view.setVisibility(View.VISIBLE);
            FrescoUtil.setResizeImage(view,images.get(0),mViewWidth);
        }else{
            SimpleDraweeView view = helper.getView(R.id.sd_image);
            view.setVisibility(View.GONE);
        }
        helper.setText(R.id.title,gankData.getDesc())
                .setText(R.id.author,gankData.getWho())
                .setText(R.id.time, TimeUtils.format(gankData.getCreatedAt(),"MM-dd"));
    }
}
