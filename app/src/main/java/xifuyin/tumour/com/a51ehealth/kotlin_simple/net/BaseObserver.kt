package xifuyin.tumour.com.a51ehealth.kotlin_simple.net


import io.reactivex.Observer
import io.reactivex.annotations.NonNull


/**
 * Created by Administrator on 2017/8/26/026.
 */

abstract class BaseObserver<T> : Observer<T> {


    abstract override fun onNext(@NonNull t: T)

    override fun onError(@NonNull e: Throwable) {

    }

    override fun onComplete() {

    }
}
