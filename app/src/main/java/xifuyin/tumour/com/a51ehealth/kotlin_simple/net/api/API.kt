package xifuyin.tumour.com.a51ehealth.kotlin_simple.net.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.ClassifyBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.ClassifyDetailBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.FocusBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.model.HomeBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.model.RankBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.model.TabInfoBean

/**
 * Created by Administrator on 2018/5/22.
 */
interface API {

    /**
     * 首页精选
     */
    @GET("v2/feed?")
    fun getFirstHomeData(@Query("num") num: Int): Observable<HomeBean>
    /**
     * 根据 nextPageUrl 请求数据下一页数据
     */
    @GET
    fun getMoreHomeData(@Url url: String): Observable<HomeBean>

    /**
     * 关注
     */
    @GET("v4/tabs/follow")
    fun getFollowInfo(): Observable<FocusBean>


    /**
     * 获取更多的关注
     */
    @GET
    fun getFollowData(@Url url: String): Observable<FocusBean>


    /**
     * 获取分类
     */
    @GET("v4/categories")
    fun getClassify(): Observable<ArrayList<ClassifyBean>>


    /**
     * 获取分类详情List
     */
    @GET("v4/categories/videoList?")
    fun getCategoryDetailList(@Query("id") id: Long): Observable<ClassifyDetailBean>

    /**
     * 获取更多的 详情List
     */
    @GET
    fun getMoreCategoryDetailListData(@Url url: String): Observable<ClassifyDetailBean>

    /**
     * 获取全部排行榜的Info（包括，title 和 Url）
     */
    @GET("v4/rankList")
    fun getRankList(): Observable<TabInfoBean>


    /**
     * 获取分类排行
     */
    @GET
    fun getRankClassifyData(@Url url: String): Observable<RankBean>

}

