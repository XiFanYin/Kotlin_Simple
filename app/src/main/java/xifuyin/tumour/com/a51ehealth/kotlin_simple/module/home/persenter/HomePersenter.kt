package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.persenter

import io.reactivex.disposables.Disposable
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BasePresenterImpl
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.model.HomeBean
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.persenter.contact.HomeContact
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.BaseObserver
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.RetrofitUtil
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.api.API
import xifuyin.tumour.com.a51ehealth.kotlin_simple.net.utils.RxSchedulers


/**
 * Created by Administrator on 2018/5/22.
 */
class HomePersenter(view: HomeContact.View) : BasePresenterImpl<HomeContact.View>(view), HomeContact.Persenter {

    private var bannerHomeBean: HomeBean? = null
    /**
     * 联网请求主页数据,第一次请求的数据结果是第二次请求的url
     */
    override fun requestHomeData(num: Int) {

        RetrofitUtil
                .instance
                .create(API::class.java)
                .getFirstHomeData(num)
                .flatMap { homeBean ->
                    //第一次请求的数据做为轮播图
                    val bannerItemList = homeBean.issueList[0].itemList
                    //过滤掉不同类型的banner
                    bannerItemList.filter { it -> it.type != "video" }.forEach { it -> bannerItemList.remove(it) }
                    //把过滤掉的数据提升成成员变量
                    bannerHomeBean = homeBean
                    // 重新赋值 Banner 长度
                    bannerHomeBean!!.issueList[0].count = bannerItemList.size
                    //第一次请求的结果是第二次请求的url，再去请求一次，做为列表数据
                    RetrofitUtil.instance.create(API::class.java).getMoreHomeData(homeBean.nextPageUrl)
                }
                .compose(RxSchedulers.io_main())
                .compose(Loading())
                .subscribe(object : BaseObserver<HomeBean>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                    }

                    override fun onNext(t: HomeBean) {
                        val listItemList = t.issueList[0].itemList
                        listItemList.filter { it -> it.type != "video" }.forEach { it -> listItemList.remove(it) }
                        //赋值过滤后的数据 + banner 数据
                        bannerHomeBean?.issueList!![0].itemList.addAll(listItemList)
                        //把数据传递给View层
                        mView!!.getData(bannerHomeBean!!)
                    }
                })


    }

}