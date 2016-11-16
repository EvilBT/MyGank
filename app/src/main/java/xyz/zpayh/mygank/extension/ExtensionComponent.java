package xyz.zpayh.mygank.extension;

import dagger.Component;
import xyz.zpayh.mygank.data.DataRepositoryComponent;
import xyz.zpayh.mygank.util.FragmentScoped;

/**
 * 文 件 名: ExtensionComponent
 * 创 建 人: 陈志鹏
 * 创建日期: 2016/11/14 23:53
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */
@FragmentScoped
@Component(dependencies = DataRepositoryComponent.class,modules = ExtensionModule.class)
public interface ExtensionComponent {
    void inject(ExtensionFragment fragment);
}
