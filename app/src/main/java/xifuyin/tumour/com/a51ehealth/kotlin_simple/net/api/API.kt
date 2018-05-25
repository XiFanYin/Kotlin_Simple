package xifuyin.tumour.com.a51ehealth.kotlin_simple.net.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.FocusBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.model.HomeBean

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

}

