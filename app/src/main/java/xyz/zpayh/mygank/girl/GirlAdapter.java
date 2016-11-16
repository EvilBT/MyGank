package xyz.zpayh.mygank.girl;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import xyz.zpayh.ganknet.data.GankData;
import xyz.zpayh.imageloader.util.FrescoUtil;
import xyz.zpayh.mygank.R;

/**
 * 文 件 名: GirlAdapter
 * 创 建 人: 陈志鹏
 * 创建日期: 2016/10/30 22:38
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public class GirlAdapter extends BaseQuickAdapter<GankData,BaseViewHolder>{

    private final int mViewWidth;

    public GirlAdapter(int viewWidth) {
        super(R.layout.item_girl, null);
        this.mViewWidth = viewWidth;
    }

    @Override
    protected void convert(BaseViewHolder helper, GankData item) {
        FrescoUtil.setWrapAndResizeImage(helper.getView(R.id.sd_image),item.getUrl(),mViewWidth);
        helper.setText(R.id.author,item.getWho())
                .setText(R.id.time,item.getDesc());
    }
}
