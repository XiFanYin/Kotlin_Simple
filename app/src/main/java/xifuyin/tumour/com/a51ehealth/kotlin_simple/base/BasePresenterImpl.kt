package xifuyin.tumour.com.a51ehealth.kotlin_simple.base

import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Administrator on 2018/5/22.
 */
open class BasePresenterImpl<V : BaseView>(mView: V) : BasePresenter {
    //这里设置为可空，未后期释放view防止内存泄漏做准备
    var mView: V? = mView
    var mCompositeDisposable: CompositeDisposable? = null


    override fun detach() {
        unDisposable()//切断流，防止rx内存泄漏，导致空指针
        mView = null
    }

    override fun addDisposable(subscription: Disposable) {

        if (mCompositeDisposable == null || mCompositeDisposable!!.isDisposed) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(subscription)
    }


    override fun unDisposable() {
        mCompositeDisposable?.dispose()
        mCompositeDisposable?.clear()
        mCompositeDisposable = null

    }


    override fun <T> Loading(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream
                    .doOnSubscribe { mView?.showLoading() }
                    .doOnError { mView?.showError() }
                    .doOnComplete { mView?.dissmassErrorView() }
                    .doFinally { mView?.dissmassLoading() }
        }
    }


}