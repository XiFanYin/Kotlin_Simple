package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter.contact

import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BasePresenter
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseView
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.FocusBean

/**
 * Created by Administrator on 2018/5/25.
 */
interface FocusContact {


    interface View : BaseView {


        fun setData(data: FocusBean, hasMore: Boolean)

        fun setMoreData(data: FocusBean, hasMore: Boolean)
    }


    interface Persenter : BasePresenter {

        fun getData();

        fun getMoreData()
    }
}