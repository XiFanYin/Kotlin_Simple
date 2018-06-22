package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.persenter.contact

import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BasePresenter
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseView
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.model.HomeBean

/**
 * Created by Administrator on 2018/5/22.
 */
interface HomeContact {


    interface View : BaseView {

        /**
         * 传递这次请求数据
         */
        fun getData(homeBean: HomeBean)

        /**
         * 传递下次请求数据
         */
        fun getNextData(homeBean: HomeBean)

    }


    interface Persenter : BasePresenter {
        /**
         * 获取首页精选数据
         */
        fun requestHomeData(num: Int)

        /**
         * 请求下页数据
         */
        fun requestNextHomeData()

    }
}