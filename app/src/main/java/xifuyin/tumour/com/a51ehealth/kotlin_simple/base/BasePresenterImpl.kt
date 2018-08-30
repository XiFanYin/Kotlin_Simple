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
        //这里不仅切断了流，而且也取消了网络请求
        mCompositeDisposable?.dispose()
        mCompositeDisposable?.clear()
        mCompositeDisposable = null

    }


    override fun <T> Loading(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream
                    .doOnSubscribe { mView?.showLoading() }//只要去请求就去显示进度条
                    .doOnError { mView?.showError() }//只要加载错误就显示错误布局
                    .doOnComplete { mView?.dissmassErrorView() }//只要加载正确就显示正确布局
                    .doFinally { mView?.dissmassLoading() }//不管加载是否成功，一定要取消加载框显示
        }
    }


}