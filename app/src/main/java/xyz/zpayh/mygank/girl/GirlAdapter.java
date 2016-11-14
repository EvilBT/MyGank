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
    }
    /*private final List<GankData> mList;

    public GirlAdapter() {
        mList = new ArrayList<>();
    }

    public void setList(List<GankData> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_girl,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final GankData data = mList.get(position);
        FrescoUtil.setWrapImage(holder.mImage,data.getUrl());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView mImage;
        public ViewHolder(View itemView) {
            super(itemView);
            mImage = (SimpleDraweeView) itemView.findViewById(R.id.sd_image);
        }
    }*/
}
