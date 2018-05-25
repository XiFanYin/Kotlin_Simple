package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter


import io.reactivex.disposables.Disposable
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BasePresenterImpl
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.FocusBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter.contact.FocusContact
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.BaseObserver
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.RetrofitUtil
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.api.API
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.utils.RxSchedulers

/**
 * Created by Administrator on 2018/5/25.
 */
class FocusPersenter(mView: FocusContact.View) : BasePresenterImpl<FocusContact.View>(mView), FocusContact.Persenter {

        lateinit var nextPageUrl :String


    /**
     * 请求关注的数据
     */
    override fun getData() {

        RetrofitUtil
                .instance
                .create(API::class.java)
                .getFollowInfo()
                .compose(RxSchedulers.io_main())
                .compose(Loading())
                .subscribe(object : BaseObserver<FocusBean>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                    }

                    override fun onNext(t: FocusBean) {
                        nextPageUrl = t.nextPageUrl
                        mView?.setData(t)

                    }


                })

    }
}