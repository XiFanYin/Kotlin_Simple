package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter

import io.reactivex.disposables.Disposable
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BasePresenterImpl
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.ClassifyDetailBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter.contact.ClassifyDetailContact
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.BaseObserver
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.RetrofitUtil
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.api.API
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.utils.RxSchedulers

/**
 * Created by Administrator on 2018/5/28.
 */
class ClassifyDetailPersenter(View: ClassifyDetailContact.View) : BasePresenterImpl<ClassifyDetailContact.View>(View), ClassifyDetailContact.Persenter {


    /**
     * 获取数据
     */
    override fun getData(id: Long) {
        RetrofitUtil
                .instance
                .create(API::class.java)
                .getCategoryDetailList(id)
                .compose(RxSchedulers.io_main())
                .compose(Loading())
                .subscribe(object : BaseObserver<ClassifyDetailBean>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                    }

                    override fun onNext(t: ClassifyDetailBean) {
                        mView?.setData(t)
                    }


                })

    }
}