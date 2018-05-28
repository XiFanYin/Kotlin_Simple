package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.persenter.contact

import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BasePresenter
import xifuyin.tumour.com.a51ehealth.kotlin_simple.base.BaseView
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.ClassifyDetailBean

/**
 * Created by Administrator on 2018/5/28.
 */
interface ClassifyDetailContact {


    interface View : BaseView {

        fun setData(data: ClassifyDetailBean)
    }


    interface Persenter : BasePresenter {

        fun getData(id: Long)


    }
}