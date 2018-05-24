package xifuyin.tumour.com.a51ehealth.kotlin_simple.utils

import android.content.Context
import android.net.ConnectivityManager
import xifuyin.tumour.com.a51ehealth.kotlin_simple.app.App


/**
 * Created by Administrator on 2018/5/24.
 */
object NetWorkUtils {

    /**
     * 判断联网状态时候可用
     */
    fun isNetworkReachable(): Boolean {
        var isAvailable = false
        val cm = App.application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val current = cm.activeNetworkInfo ?: return false
        return current.isAvailable
    }
}