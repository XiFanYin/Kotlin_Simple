package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.persenter

import io.reactivex.disposables.Disposable
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BasePresenterImpl
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.model.RankBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.persenter.contact.RankContact
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.BaseObserver
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.RetrofitUtil
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.api.API
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.utils.RxSchedulers

/**
 * Created by Administrator on 2018/5/28.
 */
class RankPersenter(View: RankContact.View) : BasePresenterImpl<RankContact.View>(View), RankContact.Persenter {

    override fun getRankData(url: String) {

        RetrofitUtil
                .instance
                .create(API::class.java)
                .getRankClassifyData(url)
                .compose(RxSchedulers.io_main())
                .compose(Loading())
                .subscribe(object : BaseObserver<RankBean>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                    }
                    override fun onNext(t: RankBean) {
                        mView?.setRankData(t)
                    }
                })


    }


}