package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.persenter

import io.reactivex.disposables.Disposable
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BasePresenterImpl
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.model.TabInfoBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.persenter.contact.HotContact
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.BaseObserver
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.RetrofitUtil
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.api.API
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.utils.RxSchedulers

/**
 * Created by Administrator on 2018/5/28.
 */

class HotPersenter(View: HotContact.View) : BasePresenterImpl<HotContact.View>(View), HotContact.Persenter {

    override fun getTableData() {

        RetrofitUtil
                .create(API::class.java)
                .getRankList()
                .compose(RxSchedulers.io_main())
                .compose(Loading())
                .subscribe(object : BaseObserver<TabInfoBean>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                    }

                    override fun onNext(t: TabInfoBean) {
                        mView?.setTableData(t)
                    }
                })

    }


}
