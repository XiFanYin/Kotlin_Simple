package NetWorkUtils

import android.content.Context
import android.net.ConnectivityManager
import xifuyin.tumour.com.a51ehealth.kotlin_simple.app.App

/**
 * 判断联网状态时候可用
 */
fun isNetworkReachable(): Boolean {
    var isAvailable = false
    val cm = App.application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val current = cm.activeNetworkInfo ?: return false
    return current.isAvailable
}
