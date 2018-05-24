package xifuyin.tumour.com.a51ehealth.kotlin_simple.base

/**
 * Created by Administrator on 2018/5/21.
 */
interface BaseView {

    //显示加载提示
    fun showLoading()

    //隐藏加载提示
    fun dissmassLoading()

    //显示错误提示布局
    fun showError()

    //隐藏显示错误提示布局
    fun dissmassErrorView()
}