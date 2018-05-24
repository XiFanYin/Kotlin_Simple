package xifuyin.tumour.com.a51ehealth.kotlin_simple.base

import io.reactivex.ObservableTransformer
import io.reactivex.disposables.Disposable

/**
 * Created by Administrator on 2018/5/21.
 */
interface BasePresenter {


    //Activity关闭把view对象置为空
    fun detach()


    //将网络请求的每一个disposable添加进入CompositeDisposable，再退出时候一并注销
    fun addDisposable(subscription: Disposable)


    //注销所有请求
    fun unDisposable()


    //显示加载进度和错误处理
    fun <T> Loading(): ObservableTransformer<T, T>
}