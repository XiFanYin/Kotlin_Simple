package xifuyin.tumour.com.a51ehealth.kotlin_simple.net

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.api.Constant

/**
 * Created by Administrator on 2018/5/22.
 */

class RetrofitUtil private constructor() {

    private val mRetrofit: Retrofit

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


    companion object {
        val DEFAULT_TIMEOUT = 30

        //静态方法，提供okHttpClient对象
        private val okHttpClient: OkHttpClient
            get() = OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                    .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .build()


        //静态属性，重写get方法
        private var mInstance: RetrofitUtil? = null

        val instance: RetrofitUtil
            get() {
                if (mInstance == null) {
                    synchronized(RetrofitUtil::class.java) {
                        mInstance = RetrofitUtil()
                    }
                }
                return mInstance!!
            }

    }

    fun <T> create(service: Class<T>): T {
        return mRetrofit.create(service)
    }


}
