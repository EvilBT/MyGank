package xyz.zpayh.ganknet.service;

import retrofit2.http.GET;
import retrofit2.http.Path;
import xyz.zpayh.ganknet.data.GankIoResult;
import xyz.zpayh.retrofit2.adapter.agera.ResultRepository;

/**
 * 文 件 名: GankService
 * 创 建 人: 陈志鹏
 * 创建日期: 2016/10/30 11:57
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public interface GankService {

    @GET("api/data/{type}/{num}/{page}")
    ResultRepository<GankIoResult> getData(@Path("type") String type,
                                            @Path("page") int page,
                                            @Path("num") int num);
}
