package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter

import io.reactivex.disposables.Disposable
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BasePresenterImpl
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.ClassifyBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter.contact.ClassifyContact
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.BaseObserver
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.RetrofitUtil
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.api.API
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.utils.RxSchedulers

/**
 * Created by Administrator on 2018/5/25.
 */
class ClassifyPersenter(mView :ClassifyContact.View):BasePresenterImpl<ClassifyContact.View>(mView),ClassifyContact.Persenter {


    override fun getData() {

        RetrofitUtil
                .create(API::class.java)
                .getClassify()
                .compose(RxSchedulers.io_main())
                .compose(Loading())
                .subscribe(object :BaseObserver<ArrayList<ClassifyBean>>(){
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                    }
                    override fun onNext(t: ArrayList<ClassifyBean>) {
                        mView?.getData(t)
                    }

                })

    }
}