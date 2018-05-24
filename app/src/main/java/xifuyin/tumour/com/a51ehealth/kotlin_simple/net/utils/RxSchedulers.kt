package xifuyin.tumour.com.a51ehealth.kotlin_simple.net.utils

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by Administrator on 2017/8/26/026.
 */

object RxSchedulers {

    fun <T> io_main(): ObservableTransformer<T, T> {

        return ObservableTransformer { upstream ->
            upstream
                    .subscribeOn(Schedulers.io())//指定联网请求的线程，事件产生的线程
                    .observeOn(AndroidSchedulers.mainThread())//指定doOnTerminate的线程
        }
    }


}
