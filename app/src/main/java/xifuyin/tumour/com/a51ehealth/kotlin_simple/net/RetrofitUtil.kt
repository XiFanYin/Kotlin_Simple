package xifuyin.tumour.com.a51ehealth.kotlin_simple.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.api.Constant
import java.util.concurrent.TimeUnit

/**
 * Created by Administrator on 2018/5/22.
 */

object RetrofitUtil {

    private val mRetrofit: Retrofit
    val DEFAULT_TIMEOUT = 30
    init {
        //可以添加自定义解析器和默认的解析器
        //添加响应式编程的适配器
        mRetrofit = Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())//可以添加自定义解析器和默认的解析器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加响应式编程的适配器
                .client(okHttpClient)
                .build()
    }



    private val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .build()


    fun <T> create(service: Class<T>): T {
        return mRetrofit.create(service)
    }


}
