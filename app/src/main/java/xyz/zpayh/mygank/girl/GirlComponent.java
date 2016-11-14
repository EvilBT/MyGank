package xyz.zpayh.mygank.girl;

import dagger.Component;
import xyz.zpayh.mygank.data.DataRepositoryComponent;
import xyz.zpayh.mygank.util.FragmentScoped;

/**
 * 文 件 名: GirlComponent
 * 创 建 人: 陈志鹏
 * 创建日期: 2016/10/30 12:29
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */
@FragmentScoped
@Component(dependencies = DataRepositoryComponent.class,modules = GirlModule.class)
public interface GirlComponent {

    void inject(GirlFragment fragment);
}
