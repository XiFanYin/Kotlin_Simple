package xifuyin.tumour.com.a51ehealth.kotlin_simple.app

import android.app.Application

/**
 * Created by Administrator on 2018/5/21.
 */
class App : Application() {
    //静态属性，私有化set方法
    companion object {
        lateinit var application: App
            private set
    }


    override fun onCreate() {
        super.onCreate()
        application = this

    }


}